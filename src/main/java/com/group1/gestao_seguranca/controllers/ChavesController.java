package com.group1.gestao_seguranca.controllers;

import com.group1.gestao_seguranca.dto.chaves.ChaveAutocompleteDTO;
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

    public ChavesController(ChavesService chavesService,
                            MovimentacoesService movimentacoesService) {
        this.chavesService       = chavesService;
        this.movimentacoesService = movimentacoesService;
    }

    // ── Entrega avulsa ───────────────────────────────────────────────────
    // POST /api/chaves/entregar
    @PostMapping("/entregar")
    public ResponseEntity<Void> entregarChave(@RequestBody EntregaAvulsaDTO dto) {
        chavesService.registrarEntregaAvulsa(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // ── Chaves emprestadas ───────────────────────────────────────────────
    // GET /api/chaves/emprestadas
    @GetMapping("/emprestadas")
    public ResponseEntity<List<ChaveEmprestadaDTO>> listarEmprestadas() {
        return ResponseEntity.ok(chavesService.listarEmprestadas());
    }

    // ── Histórico completo ───────────────────────────────────────────────
    // GET /api/chaves/historico
    @GetMapping("/historico")
    public ResponseEntity<List<EntregaHistoricoDTO>> listarHistorico() {
        return ResponseEntity.ok(chavesService.listarHistorico());
    }

    // ── NOVO: Histórico do dia ───────────────────────────────────────────
    // GET /api/chaves/historico/hoje
    @GetMapping("/historico/hoje")
    public ResponseEntity<List<EntregaHistoricoDTO>> listarHistoricoDoDia() {
        return ResponseEntity.ok(chavesService.listarHistoricoDoDia());
    }

    // ── NOVO: Autocomplete chaves disponíveis (para entrega/direção) ──────
    // GET /api/chaves/autocomplete/disponiveis?q=CHV
    @GetMapping("/autocomplete/disponiveis")
    public ResponseEntity<List<ChaveAutocompleteDTO>> autocompleteDisponiveis(
            @RequestParam(defaultValue = "") String q) {
        return ResponseEntity.ok(chavesService.autocompleteDisponiveis(q));
    }

    // ── NOVO: Autocomplete chaves emprestadas (para devolução) ────────────
    // GET /api/chaves/autocomplete/emprestadas?q=CHV
    @GetMapping("/autocomplete/emprestadas")
    public ResponseEntity<List<ChaveAutocompleteDTO>> autocompleteEmprestadas(
            @RequestParam(defaultValue = "") String q) {
        return ResponseEntity.ok(chavesService.autocompleteEmprestadas(q));
    }

    // ── Devolução individual ─────────────────────────────────────────────
    // PATCH /api/chaves/devolucao/{idEntrega}?devolvidaPor=Nome
    @PatchMapping("/devolucao/{idEntrega}")
    public ResponseEntity<DevolucaoResponseDTO> registrarDevolucao(
            @PathVariable int idEntrega,
            @NotBlank(message = "O campo \"devolvidaPor\" é obrigatório")
            @RequestParam String devolvidaPor) {
        return ResponseEntity.ok(movimentacoesService.registrarDevolucao(idEntrega, devolvidaPor));
    }
}