package com.group1.gestao_seguranca.controllers;

import com.group1.gestao_seguranca.dto.consumos.ConsumosRequestDTO;
import com.group1.gestao_seguranca.dto.consumos.ConsumosResponseDTO;
import com.group1.gestao_seguranca.dto.consumos.ConsumosUltimasLeiturasDTO;
import com.group1.gestao_seguranca.services.ConsumosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consumos")
public class ConsumosController {

    private final ConsumosService service;

    public ConsumosController(ConsumosService service) {
        this.service = service;
    }

    @GetMapping("/ultimas-leituras")
    public ResponseEntity<ConsumosUltimasLeiturasDTO> ultimasLeituras() {
        return ResponseEntity.ok(service.getUltimasLeituras());
    }

    @GetMapping
    public ResponseEntity<List<ConsumosResponseDTO>> listConsumos() {
        return ResponseEntity.ok(service.listConsumos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsumosResponseDTO> searchById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.searchById(id));
    }

    @PostMapping
    public ResponseEntity<ConsumosResponseDTO> createConsumo(@RequestBody ConsumosRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createConsumos(dto));
    }

    // ==================== UPDATE ====================
    @PutMapping("/{id}")
    public ResponseEntity<ConsumosResponseDTO> updateConsumo(
            @PathVariable Integer id,
            @RequestBody ConsumosRequestDTO dto) {
        return ResponseEntity.ok(service.updateConsumo(id, dto));
    }

    // ==================== SOFT DELETE ====================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsumo(@PathVariable Integer id) {
        service.deleteConsumo(id);
        return ResponseEntity.noContent().build();
    }
}



