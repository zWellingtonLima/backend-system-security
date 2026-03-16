package com.group1.gestao_seguranca.controllers;

import com.group1.gestao_seguranca.dto.consumos.ConsumosRequestDTO;
import com.group1.gestao_seguranca.dto.consumos.ConsumosResponseDTO;
import com.group1.gestao_seguranca.services.ConsumosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consumos")
public class ConsumosController {

    private final ConsumosService service;

    public ConsumosController(ConsumosService consumosService) {
        this.service = consumosService;
    }

    @GetMapping
    public ResponseEntity<List<ConsumosResponseDTO>> listConsumos() {
        return ResponseEntity.ok(service.listConsumos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> searchById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.searchById(id));
    }

    @PostMapping
    public ResponseEntity<?> createConsumo(@RequestBody ConsumosRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createConsumos(dto));
    }

    @PutMapping
    public ResponseEntity<?> updateConsumo(@PathVariable Integer id, @RequestBody ConsumosRequestDTO dto) {
        return ResponseEntity.ok((service.updateConsumo(id, dto)));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteConsumo(@PathVariable Integer id) {
        service.deleteConsumo(id);
        return ResponseEntity.noContent().build();
    }
}




