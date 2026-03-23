package com.group1.gestao_seguranca.services;

import com.group1.gestao_seguranca.dto.chaves.ChaveBuscaDTO;
import com.group1.gestao_seguranca.dto.funcionarios.FuncionarioBuscaDTO;
import com.group1.gestao_seguranca.dto.visitantes.VisitanteBuscaDTO;
import com.group1.gestao_seguranca.enums.StatusChaveEnum;
import com.group1.gestao_seguranca.repositories.ChavesRepository;
import com.group1.gestao_seguranca.repositories.FuncionariosRepository;
import com.group1.gestao_seguranca.repositories.VisitantesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BuscaService {

    private final FuncionariosRepository funcionariosRepo;
    private final VisitantesRepository visitantesRepo;
    private final ChavesRepository chavesRepo;

    public BuscaService(FuncionariosRepository funcionariosRepo,
                        VisitantesRepository visitantesRepo,
                        ChavesRepository chavesRepo) {
        this.funcionariosRepo = funcionariosRepo;
        this.visitantesRepo = visitantesRepo;
        this.chavesRepo = chavesRepo;
    }

    @Transactional(readOnly = true)
    public List<FuncionarioBuscaDTO> buscarFuncionarios(String nome) {
        String[] t = tokenizar(nome);
        return funcionariosRepo.procurarNomeFuncionario(t[0], t[1], t[2])
                .stream()
                .map(FuncionarioBuscaDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<VisitanteBuscaDTO> buscarVisitantes(String nome) {
        String[] t = tokenizar(nome);
        return visitantesRepo.procurarNomeVisitante(t[0], t[1], t[2])
                .stream()
                .map(VisitanteBuscaDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ChaveBuscaDTO> buscarChavesDisponiveis(String q) {
        return chavesRepo.buscarDisponiveisPorTermo(q.trim(), StatusChaveEnum.DISPONIVEL)
                .stream()
                .map(ChaveBuscaDTO::from)
                .toList();
    }

    // Normaliza a pesquisa em até 3 tokens, preenchendo com "" os restantes.
    // LIKE '%' + '' + '%' = LIKE '%%' → match universal, não filtra esse token.
    private String[] tokenizar(String input) {
        String[] partes = input.trim().split("\\s+");
        String[] tokens = new String[]{"", "", ""};
        for (int i = 0; i < Math.min(partes.length, 3); i++) {
            tokens[i] = partes[i];
        }
        return tokens;
    }
}