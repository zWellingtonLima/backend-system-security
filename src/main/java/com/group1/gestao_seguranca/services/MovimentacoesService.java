package com.group1.gestao_seguranca.services;

import com.group1.gestao_seguranca.dto.chaves.EntregaChaveDTO;
import com.group1.gestao_seguranca.dto.movimentacoes.*;
import com.group1.gestao_seguranca.entities.*;
import com.group1.gestao_seguranca.enums.StatusChaveEnum;
import com.group1.gestao_seguranca.enums.TipoChaveEnum;
import com.group1.gestao_seguranca.enums.TipoVisitanteEnum;
import com.group1.gestao_seguranca.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimentacoesService {

    private final HttpServletRequest request;
    private final MovimentacoesRepository movimentacoesRepo;
    private final FuncionariosRepository funcionariosRepo;
    private final VisitantesRepository visitantesRepo;
    private final ChavesRepository chavesRepo;
    private final EntregaChavesRepository entregaChavesRepo;

    public MovimentacoesService(HttpServletRequest request,
                                MovimentacoesRepository movimentacoesRepo,
                                FuncionariosRepository funcionariosRepo,
                                VisitantesRepository visitantesRepo,
                                ChavesRepository chavesRepo,
                                EntregaChavesRepository entregaChavesRepo) {
        this.request            = request;
        this.movimentacoesRepo  = movimentacoesRepo;
        this.funcionariosRepo   = funcionariosRepo;
        this.visitantesRepo     = visitantesRepo;
        this.chavesRepo         = chavesRepo;
        this.entregaChavesRepo  = entregaChavesRepo;
    }

    private Users getUserAutenticado() {
        return (Users) request.getAttribute("usuarioAutenticado");
    }

    // ─────────────────────────────────────────────
    // CRUD existente
    // ─────────────────────────────────────────────

    @Transactional
    public MovimentacaoResponseDTO registrarEntrada(MovimentacaoRequestDTO dto) {
        Users user = getUserAutenticado();
        Movimentacoes movimentacao = new Movimentacoes();

        movimentacao.setCreateUser(user.getCreateUser());
        movimentacao.setHoraEntrada(LocalDateTime.now());
        movimentacao.setObservacoes(dto.getObservacoes());
        movimentacao.setSetorDestino(dto.getSetorDestino());

        if (dto.getIdFuncionario() != null) {
            Funcionarios func = funcionariosRepo.findById(dto.getIdFuncionario())
                    .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado."));

            if (movimentacoesRepo.existeEntradaAtiva(func.getId()))
                throw new IllegalStateException(
                        "O funcionário " + func.getNomeFuncionario() + " já possui uma entrada ativa.");

            movimentacao.setSetorDestino(func.getSetor());
            movimentacao.setFuncionario(func);
        } else {
            Visitantes visitante = visitantesRepo.findById(dto.getIdVisitante())
                    .orElseThrow(() -> new EntityNotFoundException("Visitante não encontrado."));

            if (movimentacoesRepo.existeEntradaAtivaVisitante(visitante.getId()))
                throw new IllegalStateException(
                        "O visitante " + visitante.getNomeVisitante() + " já possui uma entrada ativa.");

            movimentacao.setVisitante(visitante);
            movimentacao.setTipoVisitante(dto.getTipoVisita());
            movimentacao.setSetorDestino(dto.getSetorDestino());

            if (dto.getIdFuncionarioResponsavel() != null) {
                Funcionarios responsavel = funcionariosRepo
                        .findById(dto.getIdFuncionarioResponsavel())
                        .orElseThrow(() -> new EntityNotFoundException("Funcionário não foi encontrado."));
                movimentacao.setFuncionarioResponsavel(responsavel);
            }
        }

        movimentacao = movimentacoesRepo.save(movimentacao);

        if (dto.getEntregaChave() != null)
            registrarEntregaChave(dto.getEntregaChave(), movimentacao);

        return MovimentacaoResponseDTO.from(movimentacao);
    }

    @Transactional
    public MovimentacaoResponseDTO registrarSaida(int idMovimentacao) {
        Movimentacoes movimentacao = movimentacoesRepo.findById(idMovimentacao)
                .orElseThrow(() -> new EntityNotFoundException("Movimentação não encontrada"));

        if (movimentacao.getHoraSaida() != null)
            throw new IllegalStateException("Uma saída já foi registrada para esta movimentação");

        if (!movimentacao.isAtivo())
            throw new IllegalStateException("Não é possível registar saída numa movimentação anulada.");

        movimentacao.setHoraSaida(LocalDateTime.now());
        movimentacoesRepo.save(movimentacao);

        List<EntregaChaves> pendentes = entregaChavesRepo
                .findByMovimentacaoAndHoraDevolucaoIsNull(movimentacao);

        if (pendentes.isEmpty())
            return new MovimentacaoResponseDTO(movimentacao);

        List<EntregaPendenteDTO> pendentesDTO = pendentes.stream()
                .map(e -> {
                    TipoChaveEnum tipo = e.getChave().getTipoChave();
                    String descricao = tipo == TipoChaveEnum.CHAVE
                            ? e.getChave().getCodigoChave()
                            : e.getChave().getCodigoMolho();
                    return new EntregaPendenteDTO(e.getId(), descricao, tipo, e.getObservacoes());
                })
                .toList();

        return new MovimentacaoResponseDTO(
                movimentacao,
                "Saída registada com " + pendentes.size() + " chave(s)/molho(s) por devolver. Por favor, fazer os apontamentos necessários.",
                pendentesDTO
        );
    }

    @Transactional
    public DevolucaoResponseDTO registrarDevolucao(int idEntrega, String devolvidaPor) {
        EntregaChaves entrega = entregaChavesRepo.findById(idEntrega)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Entrega de chave não encontrada: id=" + idEntrega));

        if (entrega.getHoraDevolucao() != null)
            throw new IllegalStateException("Esta chave/molho já foi devolvido.");

        entrega.setHoraDevolucao(LocalDateTime.now());
        entrega.setDevolvidaPor(devolvidaPor);

        if (entrega.getChave() != null) {
            Chaves chave = entrega.getChave();
            chave.setStatusChave(StatusChaveEnum.DISPONIVEL);
            chavesRepo.save(chave);
        }

        return DevolucaoResponseDTO.from(entregaChavesRepo.save(entrega));
    }

    // ─────────────────────────────────────────────
    // NOVO — Atualizar
    // ─────────────────────────────────────────────

    @Transactional
    public MovimentacaoResponseDTO atualizar(int id, MovimentacaoUpdateDTO dto) {
        Movimentacoes mov = movimentacoesRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Movimentação não encontrada: id=" + id));

        if (!mov.isAtivo())
            throw new IllegalStateException(
                    "Não é possível editar uma movimentação anulada.");

        Users user = getUserAutenticado();

        if (dto.getObservacoes() != null)
            mov.setObservacoes(dto.getObservacoes());

        if (dto.getSetorDestino() != null)
            mov.setSetorDestino(dto.getSetorDestino());

        if (dto.getTipoVisita() != null) {
            if (mov.getFuncionario() != null)
                throw new IllegalArgumentException("Funcionários não têm tipo de visita.");
            mov.setTipoVisitante(TipoVisitanteEnum.valueOf(dto.getTipoVisita()));
        }

        if (dto.getIdFuncionarioResponsavel() != null) {
            if (mov.getFuncionario() != null)
                throw new IllegalArgumentException("Funcionários não têm funcionário responsável.");
            Funcionarios responsavel = funcionariosRepo
                    .findById(dto.getIdFuncionarioResponsavel())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Funcionário não encontrado: id=" + dto.getIdFuncionarioResponsavel()));
            mov.setFuncionarioResponsavel(responsavel);
        }

        // @PreUpdate trata o modifyDate automaticamente
        mov.setModifyUser(user.getCreateUser());

        return MovimentacaoResponseDTO.from(movimentacoesRepo.save(mov));
    }
    // ─────────────────────────────────────────────
    // Listagens
    // ─────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<MovimentacaoResponseDTO> listarAtivas() {
        return movimentacoesRepo
                .findByHoraSaidaIsNullOrderByHoraEntradaDesc()
                .stream()
                .filter(Movimentacoes::isAtivo)        // exclui anuladas
                .map(m -> {
                    MovimentacaoResponseDTO dto = MovimentacaoResponseDTO.from(m);
                    List<EntregaPendenteDTO> pendentes = entregaChavesRepo
                            .findByMovimentacaoAndHoraDevolucaoIsNull(m)
                            .stream()
                            .map(e -> {
                                TipoChaveEnum tipo = e.getChave().getTipoChave();
                                String descricao = tipo == TipoChaveEnum.CHAVE
                                        ? e.getChave().getCodigoChave()
                                        : e.getChave().getCodigoMolho();
                                return new EntregaPendenteDTO(e.getId(), descricao, tipo, e.getObservacoes());
                            })
                            .toList();
                    dto.setEntregasPendentes(pendentes);
                    return dto;
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MovimentacaoResponseDTO> listarTodas() {
        return movimentacoesRepo.findAllByOrderByHoraEntradaDesc()
                .stream()
                .map(MovimentacaoResponseDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MovimentacaoResponseDTO> listarPorFuncionario(int idFuncionario) {
        if (!funcionariosRepo.existsById(idFuncionario))
            throw new EntityNotFoundException("Funcionário não encontrado: id=" + idFuncionario);
        return movimentacoesRepo.findByFuncionarioIdOrderByHoraEntradaDesc(idFuncionario)
                .stream()
                .map(MovimentacaoResponseDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MovimentacaoResponseDTO> listarPorVisitante(int idVisitante) {
        if (!visitantesRepo.existsById(idVisitante))
            throw new EntityNotFoundException("Visitante não encontrado: id=" + idVisitante);
        return movimentacoesRepo.findByVisitanteIdOrderByHoraEntradaDesc(idVisitante)
                .stream()
                .map(MovimentacaoResponseDTO::from)
                .toList();
    }

    // ─────────────────────────────────────────────
    // Métodos de apoio
    // ─────────────────────────────────────────────

    void processarEntregaChave(Chaves chave, Movimentacoes mov, String observacoes) {
        if (chave.getStatusChave() != StatusChaveEnum.DISPONIVEL)
            throw new IllegalStateException(
                    "A chave " + chave.getCodigoChave() + " não está disponível.");

        EntregaChaves entrega = new EntregaChaves();
        entrega.setMovimentacao(mov);
        entrega.setChave(chave);
        entrega.setHoraEntrega(LocalDateTime.now());
        entrega.setObservacoes(observacoes);
        entrega.setFuncionarioComChave(mov.getFuncionario());
        entrega.setVisitanteComChave(mov.getVisitante());

        chave.setStatusChave(StatusChaveEnum.EMPRESTADA);
        chavesRepo.save(chave);
        entregaChavesRepo.save(entrega);
    }

    private void registrarEntregaChave(EntregaChaveDTO entregaDTO, Movimentacoes mov) {
        if (entregaChavesRepo.existsByMovimentacaoAndHoraDevolucaoIsNull(mov))
            throw new IllegalStateException("Esta entrada já possui uma chave por devolver.");

        Chaves chave = chavesRepo.findById(entregaDTO.getIdChave())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Chave não encontrada: id=" + entregaDTO.getIdChave()));

        processarEntregaChave(chave, mov, entregaDTO.getObservacoes());
    }
}