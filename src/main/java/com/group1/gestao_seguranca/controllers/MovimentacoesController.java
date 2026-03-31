package com.group1.gestao_seguranca.controllers;

import com.group1.gestao_seguranca.dto.movimentacoes.*;
import com.group1.gestao_seguranca.services.MovimentacoesService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/movimentacoes")
public class MovimentacoesController {

    private final MovimentacoesService service;

    public MovimentacoesController(MovimentacoesService service) {
        this.service = service;
    }

    // ─────────────────────────────────────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────────────────────────────────────
    // POST /api/movimentacoes/entrada
    @PostMapping("/entrada")
    public ResponseEntity<MovimentacaoResponseDTO> registrarEntrada(
            @Valid @RequestBody MovimentacaoRequestDTO dto) {
        validarRequest(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarEntrada(dto));
    }

    // ─────────────────────────────────────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────────────────────────────────────
    // PATCH /api/movimentacoes/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<MovimentacaoResponseDTO> atualizar(
            @PathVariable int id,
            @RequestBody @Valid MovimentacaoUpdateDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    // ─────────────────────────────────────────────────────────────────────
    // SOFT DELETE
    // ─────────────────────────────────────────────────────────────────────
    // PATCH /api/movimentacoes/{id}/anular?motivo=...
    @PatchMapping("/{id}/anular")
    public ResponseEntity<AnulacaoResponseDTO> anular(
            @PathVariable int id,
            @NotBlank(message = "O motivo de anulação é obrigatório")
            @RequestParam String motivo) {
        return ResponseEntity.ok(service.anular(id, motivo));
    }

    // ─────────────────────────────────────────────────────────────────────
    // AÇÕES DE SAÍDA / DEVOLUÇÃO
    // ─────────────────────────────────────────────────────────────────────
    // PATCH /api/movimentacoes/saida/{id}
    // Regista apenas a saída (mantido — avisar se há chaves pendentes)
    @PatchMapping("/saida/{id}")
    public ResponseEntity<MovimentacaoResponseDTO> registrarSaida(
            @PathVariable int id) {
        return ResponseEntity.ok(service.registrarSaida(id));
    }

    // PATCH /api/movimentacoes/saida-com-devolucao/{id}
    // Regista saída E devolve automaticamente todas as chaves pendentes
    @PatchMapping("/saida-com-devolucao/{id}")
    public ResponseEntity<SaidaComDevolucaoResponseDTO> registrarSaidaComDevolucao(
            @PathVariable int id) {
        return ResponseEntity.ok(service.registrarSaidaComDevolucao(id));
    }

    // PATCH /api/movimentacoes/devolucao/{idEntrega}?devolvidaPor=Nome
    // Devolução individual de uma chave
    @PatchMapping("/devolucao/{idEntrega}")
    public ResponseEntity<DevolucaoResponseDTO> registrarDevolucao(
            @PathVariable int idEntrega,
            @NotBlank(message = "O campo \"devolvidaPor\" é obrigatório")
            @RequestParam String devolvidaPor) {
        return ResponseEntity.ok(service.registrarDevolucao(idEntrega, devolvidaPor));
    }

    // ─────────────────────────────────────────────────────────────────────
    // LISTAGENS - READ
    // ─────────────────────────────────────────────────────────────────────
    // GET /api/movimentacoes/ativas
    @GetMapping("/ativas")
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarAtivas() {
        return ResponseEntity.ok(service.listarAtivas());
    }

    // GET /api/movimentacoes
    @GetMapping
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    // GET /api/movimentacoes/funcionario/{id}
    @GetMapping("/funcionario/{id}")
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarPorFuncionario(
            @PathVariable int id) {
        return ResponseEntity.ok(service.listarPorFuncionario(id));
    }

    // GET /api/movimentacoes/visitante/{id}
    @GetMapping("/visitante/{id}")
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarPorVisitante(
            @PathVariable int id) {
        return ResponseEntity.ok(service.listarPorVisitante(id));
    }

    // ─────────────────────────────────────────────────────────────────────
    // Validação de request
    // ─────────────────────────────────────────────────────────────────────
    private void validarRequest(MovimentacaoRequestDTO dto) {
        boolean temVisitante = dto.getIdVisitante() != null;
        boolean temFuncionario = dto.getIdFuncionario() != null;

        if (!temFuncionario && !temVisitante)
            throw new IllegalArgumentException("É obrigatório indicar um Visitante ou Funcionário");

        if (temFuncionario && temVisitante)
            throw new IllegalArgumentException("Apenas um tipo de entrada é permitido por vez");

        if (temVisitante && dto.getTipoVisita() == null)
            throw new IllegalArgumentException("Tipo de visita é obrigatório para visitantes");

        if (temFuncionario && dto.getTipoVisita() != null)
            throw new IllegalArgumentException("Funcionários não têm tipo de visita");

        if (temFuncionario && dto.getIdFuncionarioResponsavel() != null)
            throw new IllegalArgumentException("Funcionários não têm funcionário responsável");

        if (dto.getEntregaChave() != null && dto.getEntregaChave().getIdChave() == null)
            throw new IllegalArgumentException("Entrega de chave requer a identificação da Chave.");
    }
}