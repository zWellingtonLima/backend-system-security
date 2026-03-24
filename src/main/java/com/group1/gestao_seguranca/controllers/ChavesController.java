package com.group1.gestao_seguranca.controllers;

import com.group1.gestao_seguranca.dto.chaves.ChaveEmprestadaDTO;
import com.group1.gestao_seguranca.dto.chaves.EntregaAvulsaDTO;
import com.group1.gestao_seguranca.dto.chaves.EntregaHistoricoDTO;
import com.group1.gestao_seguranca.dto.movimentacoes.DevolucaoResponseDTO;
import com.group1.gestao_seguranca.services.ChavesService;
import com.group1.gestao_seguranca.services.MovimentacoesService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/chaves")
public class ChavesController {

    private final ChavesService chavesService;
    private final MovimentacoesService movimentacoesService;

    public ChavesController(ChavesService chavesService, MovimentacoesService movimentacoesService) {
        this.chavesService = chavesService;
        this.movimentacoesService = movimentacoesService;
    }

    // POST /api/chaves/entregar
    @PostMapping("/entregar")
    public ResponseEntity<Void> entregarChave(
            @RequestBody EntregaAvulsaDTO dto) {
        chavesService.registrarEntregaAvulsa(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // GET /api/chaves/emprestadas
    @GetMapping("/emprestadas")
    public ResponseEntity<List<ChaveEmprestadaDTO>> listarEmprestadas() {
        return ResponseEntity.ok(chavesService.listarEmprestadas());
    }

    // GET /api/chaves/historico
    @GetMapping("/historico")
    public ResponseEntity<List<EntregaHistoricoDTO>> listarHistorico() {
        return ResponseEntity.ok(chavesService.listarHistorico());
    }

    // PATCH /api/chaves/devolucao/{idEntrega}?devolvidaPor=Nome
    // Reutiliza o registrarDevolucao já existente no MovimentacoesService
    @PatchMapping("/devolucao/{idEntrega}")
    public ResponseEntity<DevolucaoResponseDTO> registrarDevolucao(
            @PathVariable int idEntrega,
            @NotBlank(message = "O campo \"devolvidaPor\" é obrigatório")
            @RequestParam String devolvidaPor) {
        return ResponseEntity.ok(movimentacoesService.registrarDevolucao(idEntrega, devolvidaPor));
    }
}