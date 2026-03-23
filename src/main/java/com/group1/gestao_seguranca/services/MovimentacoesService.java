package com.group1.gestao_seguranca.services;

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

    public MovimentacoesService(HttpServletRequest request, MovimentacoesRepository movimentacoesRepo, FuncionariosRepository funcionariosRepo, VisitantesRepository visitantesRepo, ChavesRepository chavesRepo, EntregaChavesRepository entregaChavesRepo) {
        this.request = request;
        this.movimentacoesRepo = movimentacoesRepo;
        this.funcionariosRepo = funcionariosRepo;
        this.visitantesRepo = visitantesRepo;
        this.chavesRepo = chavesRepo;
        this.entregaChavesRepo = entregaChavesRepo;
    }

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

            if (movimentacoesRepo.existeEntradaAtiva(func.getId())) {
                throw new IllegalStateException("O funcionário " + func.getNomeFuncionario() + " já possui uma entrada ativa.");
            }

            movimentacao.setSetorDestino(func.getSetor());
            movimentacao.setFuncionario(func);
        } else {
            Visitantes visitante = visitantesRepo.findById(dto.getIdVisitante())
                    .orElseThrow(() -> new EntityNotFoundException("Visitante não encontrado."));

            if (movimentacoesRepo.existeEntradaAtivaVisitante(visitante.getId())) {
                throw new IllegalStateException("O visitante " + visitante.getNomeVisitante() + " já possui uma entrada ativa.");
            }

            movimentacao.setVisitante(visitante);
            movimentacao.setTipoVisitante(dto.getTipoVisita());
            movimentacao.setSetorDestino(dto.getSetorDestino());

            //Funciorio responsavel opcional
            if (dto.getIdFuncionarioResponsavel() != null) {
                Funcionarios responsavel = funcionariosRepo
                        .findById(dto.getIdFuncionarioResponsavel())
                        .orElseThrow(() -> new EntityNotFoundException("Funcionário não foi encontrado."));
                movimentacao.setFuncionarioResponsavel(responsavel);
            }
        }
        movimentacao = movimentacoesRepo.save(movimentacao);

        if (dto.getEntregaChave() != null) {
            registrarEntregaChave(dto.getEntregaChave(), movimentacao);
        }

        return MovimentacaoResponseDTO.from(movimentacao);
    }


    @Transactional
    public MovimentacaoResponseDTO registrarSaida(int idMovimentacao) {
        Movimentacoes movimentacao = movimentacoesRepo.findById(idMovimentacao)
                .orElseThrow(() -> new EntityNotFoundException("Movimentação não encontrada"));

        if (movimentacao.getHoraSaida() != null) {
            throw new IllegalStateException("Uma saída já foi registrada para esta movimentação");
        }

        movimentacao.setHoraSaida(LocalDateTime.now());
        movimentacoesRepo.save(movimentacao);

        // Aqui verifica se existem pendencias de chaves mas nao bloqueia a saida
        List<EntregaChaves> pendentes = entregaChavesRepo.findByMovimentacaoAndHoraDevolucaoIsNull(movimentacao);

        if (pendentes.isEmpty()) {
            return new MovimentacaoResponseDTO(movimentacao);
        }

        List<EntregaPendenteDTO> pendentesDTO = pendentes.stream()
                .map(entregasChave -> {
                    TipoChaveEnum tipo = entregasChave.getChave().getTipoChave();
                    String descricao;

                    // Verifica de qual tipo e a chave para colocar a descricao adequada
                    if (tipo == TipoChaveEnum.CHAVE) {
                        descricao = entregasChave.getChave().getCodigoChave();
                    } else {
                        descricao = entregasChave.getChave().getCodigoMolho();
                    }

                    return new EntregaPendenteDTO(entregasChave.getId(), descricao, tipo, entregasChave.getObservacoes());
                })
                .toList();

        return new MovimentacaoResponseDTO(
                movimentacao,
                "Saída registada com " + pendentes.size() + " chave(s)/molho(s) por devolver." + "Por favor, fazer os apontamentos necessários.",
                pendentesDTO
        );
    }

    @Transactional
    public DevolucaoResponseDTO registrarDevolucao(int idEntrega, String devolvidaPor) {

        EntregaChaves entrega = entregaChavesRepo.findById(idEntrega)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Entrega de chave não encontrada: id=" + idEntrega));

        if (entrega.getHoraDevolucao() != null) {
            throw new IllegalStateException("Esta chave/molho já foi devolvido.");
        }

        entrega.setHoraDevolucao(LocalDateTime.now());
        entrega.setDevolvidaPor(devolvidaPor);

        // Volta o status para DISPONIVEL
        if (entrega.getChave() != null) {
            Chaves chave = entrega.getChave();
            chave.setStatusChave(StatusChaveEnum.DISPONIVEL);
            chavesRepo.save(chave);
        }

        return DevolucaoResponseDTO.from(entregaChavesRepo.save(entrega));
    }

    // ---------------
    // Metodos de Listagem
    @Transactional(readOnly = true)
    public List<MovimentacaoResponseDTO> listarAtivas() {
        return movimentacoesRepo.findByHoraSaidaIsNullOrderByHoraEntradaDesc()
                .stream()
                .map(MovimentacaoResponseDTO::from)
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

    // ----------------
    // Metodos de apoio
    private Users getUserAutenticado() {
        return (Users) request.getAttribute("usuarioAutenticado");
    }

    private void registrarEntregaChave(EntregaChaveDTO entregaDTO, Movimentacoes mov) {
        Users user = getUserAutenticado();

        EntregaChaves entrega = new EntregaChaves();
        entrega.setCreateUser(user.getNomeSeguranca());
        entrega.setCreateDate(LocalDateTime.now());
        entrega.setMovimentacao(mov);
        entrega.setHoraEntrega(LocalDateTime.now());
        entrega.setObservacoes(entregaDTO.getObservacoes());

        // Associa o mesmo funcionário OU visitante da movimentação
        entrega.setFuncionarioComChave(mov.getFuncionario());
        entrega.setVisitanteComChave(mov.getVisitante());

        if (entregaDTO.getIdChave() != null) {
            Chaves chave = chavesRepo.findById(entregaDTO.getIdChave())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Chave não encontrada: id=" + entregaDTO.getIdChave()));

            // Verifica se a chave esta disponivel
            if (StatusChaveEnum.DISPONIVEL != chave.getStatusChave()) {
                throw new IllegalStateException(
                        "A chave " + chave.getCodigoChave() + " não está disponível.");
            }

            chave.setStatusChave(StatusChaveEnum.EMPRESTADA);
            chavesRepo.save(chave);
            entrega.setChave(chave);
        }

        entregaChavesRepo.save(entrega);
    }
}
