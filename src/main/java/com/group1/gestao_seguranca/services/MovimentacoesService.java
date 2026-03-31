package com.group1.gestao_seguranca.services;

import com.group1.gestao_seguranca.dto.chaves.EntregaChaveDTO;
import com.group1.gestao_seguranca.dto.movimentacoes.*;
import com.group1.gestao_seguranca.entities.*;
import com.group1.gestao_seguranca.enums.StatusChaveEnum;
import com.group1.gestao_seguranca.enums.TipoChaveEnum;
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
        this.request = request;
        this.movimentacoesRepo = movimentacoesRepo;
        this.funcionariosRepo = funcionariosRepo;
        this.visitantesRepo = visitantesRepo;
        this.chavesRepo = chavesRepo;
        this.entregaChavesRepo = entregaChavesRepo;
    }

    private Users getUserAutenticado() {
        return (Users) request.getAttribute("usuarioAutenticado");
    }

    // ─────────────────────────────────────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────────────────────────────────────
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

    // ─────────────────────────────────────────────────────────────────────
    // UPDATE — Atualizar campos da movimentação
    // ─────────────────────────────────────────────────────────────────────
    @Transactional
    public MovimentacaoResponseDTO atualizar(int id, MovimentacaoUpdateDTO dto) {
        Movimentacoes mov = movimentacoesRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Movimentação não encontrada: id=" + id));

        if (!mov.isAtivo())
            throw new IllegalStateException("Não é possível editar uma movimentação anulada.");

        Users user = getUserAutenticado();
        boolean temSaida = mov.getHoraSaida() != null;

        // ─ Campos editáveis sempre ──────────────────────────────────────
        if (dto.getObservacoes() != null)
            mov.setObservacoes(dto.getObservacoes());

        // ── Campos bloqueados após saída ─────────────────────────────────
        if (temSaida) {
            boolean tentouEditarCampoBloqueado =
                    dto.getSetorDestino() != null
                            || dto.getIdFuncionario() != null
                            || dto.getIdVisitante() != null
                            || dto.getTipoVisita() != null
                            || dto.getIdFuncionarioResponsavel() != null;

            if (tentouEditarCampoBloqueado)
                throw new IllegalStateException(
                        "Esta movimentação já tem saída registada. Apenas as observações podem ser alteradas.");

            mov.setModifyUser(user.getCreateUser());
            return MovimentacaoResponseDTO.from(movimentacoesRepo.save(mov));
        }

        // ── Campos editáveis só enquanto ativa ──────────────────────────
        if (dto.getSetorDestino() != null)
            mov.setSetorDestino(dto.getSetorDestino());

        if (dto.getTipoVisita() != null) {
            if (mov.getFuncionario() != null)
                throw new IllegalArgumentException("Funcionários não têm tipo de visita.");
            mov.setTipoVisitante(dto.getTipoVisita());
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

        // ── Trocar pessoa ────────────────────────────────────────────────
        if (dto.getIdFuncionario() != null) {
            if (mov.getVisitante() != null)
                throw new IllegalArgumentException(
                        "Não é possível trocar visitante por funcionário numa entrada já criada.");

            Funcionarios novoFunc = funcionariosRepo.findById(dto.getIdFuncionario())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Funcionário não encontrado: id=" + dto.getIdFuncionario()));

            // Valida entrada ativa noutra movimentação (ignora a atual)
            boolean outraEntradaAtiva = movimentacoesRepo
                    .existeEntradaAtiva(novoFunc.getId())
                    && novoFunc.getId() != (mov.getFuncionario() != null ? mov.getFuncionario().getId() : -1);

            if (outraEntradaAtiva)
                throw new IllegalStateException(
                        "O funcionário " + novoFunc.getNomeFuncionario() + " já possui uma entrada ativa.");

            mov.setFuncionario(novoFunc);
            mov.setSetorDestino(novoFunc.getSetor());
        }

        if (dto.getIdVisitante() != null) {
            if (mov.getFuncionario() != null)
                throw new IllegalArgumentException(
                        "Não é possível trocar funcionário por visitante numa entrada já criada.");

            Visitantes novoVisitante = visitantesRepo.findById(dto.getIdVisitante())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Visitante não encontrado: id=" + dto.getIdVisitante()));

            boolean outraEntradaAtiva = movimentacoesRepo
                    .existeEntradaAtivaVisitante(novoVisitante.getId())
                    && novoVisitante.getId() != (mov.getVisitante() != null ? mov.getVisitante().getId() : -1);

            if (outraEntradaAtiva)
                throw new IllegalStateException(
                        "O visitante " + novoVisitante.getNomeVisitante() + " já possui uma entrada ativa.");

            mov.setVisitante(novoVisitante);
        }

        mov.setModifyUser(user.getCreateUser());
        return MovimentacaoResponseDTO.from(movimentacoesRepo.save(mov));
    }

    // ─────────────────────────────────────────────────────────────────────
    // SOFT DELETE — Anular movimentação
    // ─────────────────────────────────────────────────────────────────────
    @Transactional
    public AnulacaoResponseDTO anular(int id, String motivo) {
        Movimentacoes mov = movimentacoesRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Movimentação não encontrada: id=" + id));

        if (!mov.isAtivo())
            throw new IllegalStateException("Esta movimentação já foi anulada.");

        Users user = getUserAutenticado();

        // Devolve automaticamente todas as chaves pendentes
        List<EntregaChaves> pendentes = entregaChavesRepo
                .findByMovimentacaoAndHoraDevolucaoIsNull(mov);

        List<String> chavesDevolvidas = pendentes.stream()
                .map(this::devolverChaveAutomaticamente)
                .toList();

        // Anula a movimentação
        mov.setAtivo(false);
        mov.setMotivoAnulacao(motivo);
        mov.setDataAnulacao(LocalDateTime.now());
        mov.setAnuladoPor(user.getCreateUser());
        mov.setModifyUser(user.getCreateUser());

        MovimentacaoResponseDTO responseDTO = MovimentacaoResponseDTO.from(movimentacoesRepo.save(mov));

        if (chavesDevolvidas.isEmpty())
            return new AnulacaoResponseDTO(responseDTO);

        return new AnulacaoResponseDTO(
                responseDTO,
                chavesDevolvidas.size() + " chave(s)/molho(s) devolvido(s) automaticamente com a anulação.",
                chavesDevolvidas
        );
    }

    // ─────────────────────────────────────────────────────────────────────
    // Saída + devolução automática de chaves (ação combinada)
    // ─────────────────────────────────────────────────────────────────────

    @Transactional
    public SaidaComDevolucaoResponseDTO registrarSaidaComDevolucao(int idMovimentacao) {
        Movimentacoes mov = movimentacoesRepo.findById(idMovimentacao)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Movimentação não encontrada: id=" + idMovimentacao));

        if (!mov.isAtivo())
            throw new IllegalStateException("Não é possível registar saída numa movimentação anulada.");

        if (mov.getHoraSaida() != null)
            throw new IllegalStateException("Uma saída já foi registada para esta movimentação.");

        mov.setHoraSaida(LocalDateTime.now());
        movimentacoesRepo.save(mov);

        List<EntregaChaves> pendentes = entregaChavesRepo
                .findByMovimentacaoAndHoraDevolucaoIsNull(mov);

        if (pendentes.isEmpty())
            return new SaidaComDevolucaoResponseDTO(MovimentacaoResponseDTO.from(mov));

        List<String> chavesDevolvidas = pendentes.stream()
                .map(this::devolverChaveAutomaticamente)
                .toList();

        return new SaidaComDevolucaoResponseDTO(
                MovimentacaoResponseDTO.from(mov),
                chavesDevolvidas.size() + " chave(s)/molho(s) devolvido(s) automaticamente com a saída.",
                chavesDevolvidas
        );
    }

    // ─────────────────────────────────────────────────────────────────────
    // Registo de saída simples (mantido para retrocompatibilidade)
    // ─────────────────────────────────────────────────────────────────────

    @Transactional
    public MovimentacaoResponseDTO registrarSaida(int idMovimentacao) {
        Movimentacoes movimentacao = movimentacoesRepo.findById(idMovimentacao)
                .orElseThrow(() -> new EntityNotFoundException("Movimentação não encontrada"));

        if (movimentacao.getHoraSaida() != null)
            throw new IllegalStateException("Uma saída já foi registada para esta movimentação.");

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

    // ─────────────────────────────────────────────────────────────────────
    // Devolução individual de chave
    // ─────────────────────────────────────────────────────────────────────

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

    // ─────────────────────────────────────────────────────────────────────
    // Listagens
    // ─────────────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<MovimentacaoResponseDTO> listarAtivas() {
        return movimentacoesRepo
                .findByHoraSaidaIsNullOrderByHoraEntradaDesc()
                .stream()
                .filter(Movimentacoes::isAtivo)
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

    // ─────────────────────────────────────────────────────────────────────
    // Métodos de apoio
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Devolve uma chave automaticamente (usado na anulação e na saída-com-devolução).
     * Retorna a descrição da chave para incluir no aviso ao utilizador.
     */
    private String devolverChaveAutomaticamente(EntregaChaves entrega) {
        String devolvidaPor = entrega.getFuncionarioComChave() != null
                ? entrega.getFuncionarioComChave().getNomeFuncionario()
                : entrega.getVisitanteComChave() != null
                ? entrega.getVisitanteComChave().getNomeVisitante()
                : "Desconhecido";

        entrega.setHoraDevolucao(LocalDateTime.now());
        entrega.setDevolvidaPor(devolvidaPor);

        Chaves chave = entrega.getChave();
        TipoChaveEnum tipo = chave.getTipoChave();
        String descricao = tipo == TipoChaveEnum.CHAVE
                ? chave.getCodigoChave()
                : chave.getCodigoMolho();

        chave.setStatusChave(StatusChaveEnum.DISPONIVEL);
        chavesRepo.save(chave);
        entregaChavesRepo.save(entrega);

        return descricao;
    }

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