package com.group1.gestao_seguranca.controllers;

import com.group1.gestao_seguranca.dto.ocorrencias.OcorrenciasRequestDTO;
import com.group1.gestao_seguranca.dto.ocorrencias.OcorrenciasResponseDTO;
import com.group1.gestao_seguranca.enums.EstadoOcorrenciaEnum;
import com.group1.gestao_seguranca.services.OcorrenciasService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ocorrencias")
public class OcorrenciaController {

    private final OcorrenciasService service;

    public OcorrenciaController(OcorrenciasService service) {
        this.service = service;
    }

    // GET /api/ocorrencias
    @GetMapping
    public ResponseEntity<List<OcorrenciasResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // NOVO: GET /api/ocorrencias/hoje
    @GetMapping("/hoje")
    public ResponseEntity<List<OcorrenciasResponseDTO>> listarDoDia() {
        return ResponseEntity.ok(service.listarDoDia());
    }

    // POST /api/ocorrencias
    @PostMapping
    public ResponseEntity<OcorrenciasResponseDTO> criar(
            @Valid @RequestBody OcorrenciasRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criar(dto));
    }

    // GET /api/ocorrencias/{id}
    @GetMapping("/{id}")
    public ResponseEntity<OcorrenciasResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // PUT /api/ocorrencias/{id}
    @PutMapping("/{id}")
    public ResponseEntity<OcorrenciasResponseDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody OcorrenciasRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    // PATCH /api/ocorrencias/{id}/estado
    @PatchMapping("/{id}/estado")
    public ResponseEntity<OcorrenciasResponseDTO> atualizarEstado(
            @PathVariable Integer id,
            @RequestParam EstadoOcorrenciaEnum estado) {
        return ResponseEntity.ok(service.atualizarEstado(id, estado));
    }

    // DELETE removido — botão "eliminar" retirado das ocorrências
}