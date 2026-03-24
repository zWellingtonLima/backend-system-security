package com.group1.gestao_seguranca.services;

import com.group1.gestao_seguranca.dto.chaves.ChaveEmprestadaDTO;
import com.group1.gestao_seguranca.dto.chaves.EntregaAvulsaDTO;
import com.group1.gestao_seguranca.dto.chaves.EntregaHistoricoDTO;
import com.group1.gestao_seguranca.entities.Chaves;
import com.group1.gestao_seguranca.entities.Movimentacoes;
import com.group1.gestao_seguranca.repositories.ChavesRepository;
import com.group1.gestao_seguranca.repositories.EntregaChavesRepository;
import com.group1.gestao_seguranca.repositories.MovimentacoesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChavesService {

    private final EntregaChavesRepository entregaChavesRepo;
    private final MovimentacoesRepository movimentacoesRepo;
    private final ChavesRepository chavesRepo;
    private final MovimentacoesService movimentacoesService;

    public ChavesService(EntregaChavesRepository entregaChavesRepo, MovimentacoesRepository movimentacoesRepo, ChavesRepository chavesRepo, MovimentacoesService movimentacoesService) {
        this.entregaChavesRepo = entregaChavesRepo;
        this.movimentacoesRepo = movimentacoesRepo;
        this.chavesRepo = chavesRepo;
        this.movimentacoesService = movimentacoesService;
    }

    @Transactional(readOnly = true)
    public List<ChaveEmprestadaDTO> listarEmprestadas() {
        return entregaChavesRepo.findByHoraDevolucaoIsNullOrderByHoraEntregaDesc()
                .stream()
                .map(ChaveEmprestadaDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EntregaHistoricoDTO> listarHistorico() {
        return entregaChavesRepo.findByHoraDevolucaoIsNotNullOrderByHoraDevolucaoDesc()
                .stream()
                .map(EntregaHistoricoDTO::from)
                .toList();
    }

    @Transactional
    public void registrarEntregaAvulsa(EntregaAvulsaDTO dto) {
        Movimentacoes mov = movimentacoesRepo.findById(dto.getIdMovimentacao())
                .orElseThrow(() -> new EntityNotFoundException("Movimentação não encontrada."));

        if (mov.getHoraSaida() != null)
            throw new IllegalStateException("Esta pessoa já registou saída.");

        boolean jaTemChave = entregaChavesRepo
                .existsByMovimentacaoAndHoraDevolucaoIsNull(mov);
        if (jaTemChave)
            throw new IllegalStateException(
                    "Esta pessoa já possui uma chave por devolver.");

        Chaves chave = chavesRepo.findById(dto.getIdChave())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Chave não encontrada: id=" + dto.getIdChave()));

        // Delega a lógica comum
        movimentacoesService.processarEntregaChave(chave, mov, null);
    }
}