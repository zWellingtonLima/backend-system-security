package com.group1.gestao_seguranca.controllers;

import com.group1.gestao_seguranca.dto.movimentacoes.*;
import com.group1.gestao_seguranca.services.MovimentacoesService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @PostMapping("/entrada")
    public ResponseEntity<MovimentacaoResponseDTO> registrarEntrada(
            @Valid @RequestBody MovimentacaoRequestDTO dto) {
        validarRequest(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarEntrada(dto));
    }

    // ─────────────────────────────────────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────────────────────────────────────
    @PatchMapping("/{id}")
    public ResponseEntity<MovimentacaoResponseDTO> atualizar(
            @PathVariable int id,
            @RequestBody @Valid MovimentacaoUpdateDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    // ─────────────────────────────────────────────────────────────────────
    // SOFT DELETE
    // ─────────────────────────────────────────────────────────────────────
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
    @PatchMapping("/saida/{id}")
    public ResponseEntity<MovimentacaoResponseDTO> registrarSaida(
            @PathVariable int id) {
        return ResponseEntity.ok(service.registrarSaida(id));
    }

    @PatchMapping("/saida-com-devolucao/{id}")
    public ResponseEntity<SaidaComDevolucaoResponseDTO> registrarSaidaComDevolucao(
            @PathVariable int id) {
        return ResponseEntity.ok(service.registrarSaidaComDevolucao(id));
    }

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
    @GetMapping("/ativas")
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarAtivas() {
        return ResponseEntity.ok(service.listarAtivas());
    }

    @GetMapping
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/funcionario/{id}")
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarPorFuncionario(
            @PathVariable int id) {
        return ResponseEntity.ok(service.listarPorFuncionario(id));
    }

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

        // ── Validação da lista de chaves ─────────────────────────────────
        if (dto.getEntregasChave() != null && !dto.getEntregasChave().isEmpty()) {

            dto.getEntregasChave().forEach(e -> {
                if (e.getIdChave() == null)
                    throw new IllegalArgumentException(
                            "Cada entrega de chave requer a identificação da Chave.");
            });

            // Chaves duplicadas na mesma requisição
            Set<Integer> idsVistos = new HashSet<>();
            dto.getEntregasChave().forEach(e -> {
                if (!idsVistos.add(e.getIdChave()))
                    throw new IllegalArgumentException(
                            "A chave id=" + e.getIdChave() + " foi indicada mais de uma vez na mesma entrada.");
            });
        }
    }
}