package com.group1.gestao_seguranca.controllers;

import com.group1.gestao_seguranca.dto.visitantes.VisitantesRequestDTO;
import com.group1.gestao_seguranca.dto.visitantes.VisitantesResponseDTO;
import com.group1.gestao_seguranca.services.VisitantesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visitantes")
public class VisitantesController {

    private final VisitantesService service;

    public VisitantesController(VisitantesService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<VisitantesResponseDTO> criar(
            @Valid @RequestBody VisitantesRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<VisitantesResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitantesResponseDTO> buscarPorId(@PathVariable int id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/documento/{documento}")
    public ResponseEntity<VisitantesResponseDTO> buscarPorDocumento(@PathVariable String documento) {
        return ResponseEntity.ok(service.buscarPorDocumento(documento));
    }

    // ==================== UPDATE ====================
    @PutMapping("/{id}")
    public ResponseEntity<VisitantesResponseDTO> atualizar(
            @PathVariable int id,
            @Valid @RequestBody VisitantesRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    // ==================== SOFT DELETE ====================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable int id) {
        service.softDelete(id);
        return ResponseEntity.noContent().build();
    }
}