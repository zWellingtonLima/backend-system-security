package com.group1.gestao_seguranca.services;

import com.group1.gestao_seguranca.dto.chaves.ChaveAutocompleteDTO;
import com.group1.gestao_seguranca.dto.chaves.ChaveEmprestadaDTO;
import com.group1.gestao_seguranca.dto.chaves.EntregaAvulsaDTO;
import com.group1.gestao_seguranca.dto.chaves.EntregaHistoricoDTO;
import com.group1.gestao_seguranca.entities.Chaves;
import com.group1.gestao_seguranca.entities.Movimentacoes;
import com.group1.gestao_seguranca.enums.StatusChaveEnum;
import com.group1.gestao_seguranca.repositories.ChavesRepository;
import com.group1.gestao_seguranca.repositories.EntregaChavesRepository;
import com.group1.gestao_seguranca.repositories.MovimentacoesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChavesService {

    private final EntregaChavesRepository entregaChavesRepo;
    private final MovimentacoesRepository movimentacoesRepo;
    private final ChavesRepository chavesRepo;
    private final MovimentacoesService movimentacoesService;

    public ChavesService(EntregaChavesRepository entregaChavesRepo,
                         MovimentacoesRepository movimentacoesRepo,
                         ChavesRepository chavesRepo,
                         MovimentacoesService movimentacoesService) {
        this.entregaChavesRepo   = entregaChavesRepo;
        this.movimentacoesRepo   = movimentacoesRepo;
        this.chavesRepo          = chavesRepo;
        this.movimentacoesService = movimentacoesService;
    }

    // ── Chaves emprestadas (ativas) ──────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ChaveEmprestadaDTO> listarEmprestadas() {
        return entregaChavesRepo.findByHoraDevolucaoIsNullOrderByHoraEntregaDesc()
                .stream()
                .map(ChaveEmprestadaDTO::from)
                .toList();
    }

    // ── Histórico completo (devolvidas) ──────────────────────────────────
    @Transactional(readOnly = true)
    public List<EntregaHistoricoDTO> listarHistorico() {
        return entregaChavesRepo.findByHoraDevolucaoIsNotNullOrderByHoraDevolucaoDesc()
                .stream()
                .map(EntregaHistoricoDTO::from)
                .toList();
    }

    // ── NOVO: Histórico filtrado pelo dia de hoje ─────────────────────────
    @Transactional(readOnly = true)
    public List<EntregaHistoricoDTO> listarHistoricoDoDia() {
        LocalDateTime inicioDia = LocalDate.now().atStartOfDay();
        LocalDateTime fimDia    = inicioDia.plusDays(1);
        return entregaChavesRepo
                .findByHoraDevolucaoBetweenOrderByHoraDevolucaoDesc(inicioDia, fimDia)
                .stream()
                .map(EntregaHistoricoDTO::from)
                .toList();
    }

    // ── NOVO: Autocomplete para devolução (chaves emprestadas) ────────────
    @Transactional(readOnly = true)
    public List<ChaveAutocompleteDTO> autocompleteEmprestadas(String q) {
        String termo = q == null ? "" : q.trim();
        return chavesRepo.buscarEmprestadaPorTermo(termo, StatusChaveEnum.EMPRESTADA)
                .stream()
                .map(ChaveAutocompleteDTO::from)
                .toList();
    }

    // ── NOVO: Autocomplete para entrega (chaves disponíveis) ──────────────
    //    Usado tanto na direção de funcionários como na de visitantes
    @Transactional(readOnly = true)
    public List<ChaveAutocompleteDTO> autocompleteDisponiveis(String q) {
        String termo = q == null ? "" : q.trim();
        return chavesRepo.buscarDisponiveisPorTermo(termo, StatusChaveEnum.DISPONIVEL)
                .stream()
                .map(ChaveAutocompleteDTO::from)
                .toList();
    }

    // ── Entrega avulsa (já existia) ──────────────────────────────────────
    @Transactional
    public void registrarEntregaAvulsa(EntregaAvulsaDTO dto) {
        Movimentacoes mov = movimentacoesRepo.findById(dto.getIdMovimentacao())
                .orElseThrow(() -> new EntityNotFoundException("Movimentação não encontrada."));

        if (mov.getHoraSaida() != null)
            throw new IllegalStateException("Esta pessoa já registou saída.");

        Chaves chave = chavesRepo.findById(dto.getIdChave())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Chave não encontrada: id=" + dto.getIdChave()));

        movimentacoesService.processarEntregaChave(chave, mov, null);
    }
}