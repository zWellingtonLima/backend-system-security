package com.group1.gestao_seguranca.controller;

import com.group1.gestao_seguranca.dto.visitantes.VisitantesRequestDTO;
import com.group1.gestao_seguranca.dto.visitantes.VisitantesResponseDTO;
import com.group1.gestao_seguranca.service.VisitantesService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/visitantes")
@Validated
public class VisitantesController {

    private final VisitantesService service;

    public VisitantesController(VisitantesService service) {
        this.service = service;
    }

    // ==================== CREATE ====================
    @PostMapping
    public ResponseEntity<VisitantesResponseDTO> criar(
            @Valid @RequestBody VisitantesRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.criar(dto));
    }

    // ==================== LIST | SEARCH ====================
    @GetMapping
    public ResponseEntity<Page<VisitantesResponseDTO>> listarTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "nomeVisitante") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(service.listarVisitantesAtivos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitantesResponseDTO> buscarPorId(@PathVariable int id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/documento/{documento}")
    public ResponseEntity<VisitantesResponseDTO> buscarPorDocumento(
            @PathVariable @Size(max = 30) String documento
    ) {
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