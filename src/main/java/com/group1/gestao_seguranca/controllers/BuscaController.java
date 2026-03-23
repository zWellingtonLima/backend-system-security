package com.group1.gestao_seguranca.controllers;

import com.group1.gestao_seguranca.dto.chaves.ChaveBuscaDTO;
import com.group1.gestao_seguranca.dto.funcionarios.FuncionarioBuscaDTO;
import com.group1.gestao_seguranca.dto.visitantes.VisitanteBuscaDTO;
import com.group1.gestao_seguranca.services.BuscaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/busca")
public class BuscaController {

    private final BuscaService service;

    public BuscaController(BuscaService service) {
        this.service = service;
    }

    // GET /api/busca/funcionarios?nome=jo
    @GetMapping("/funcionarios")
    public ResponseEntity<List<FuncionarioBuscaDTO>> funcionarios(
            @RequestParam String nome) {
        if (nome.isBlank() || nome.length() < 2)
            return ResponseEntity.ok(List.of());
        return ResponseEntity.ok(service.buscarFuncionarios(nome));
    }

    // GET /api/busca/visitantes?nome=ma
    @GetMapping("/visitantes")
    public ResponseEntity<List<VisitanteBuscaDTO>> visitantes(
            @RequestParam String nome) {
        if (nome.isBlank() || nome.length() < 2)
            return ResponseEntity.ok(List.of());
        return ResponseEntity.ok(service.buscarVisitantes(nome));
    }

    // GET /api/busca/chaves?q=CHV
    @GetMapping("/chaves")
    public ResponseEntity<List<ChaveBuscaDTO>> chaves(
            @RequestParam String q) {
        if (q.isBlank())
            return ResponseEntity.ok(List.of());
        return ResponseEntity.ok(service.buscarChavesDisponiveis(q));
    }
}