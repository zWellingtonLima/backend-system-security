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

    private final ConsumosService consumosService;

    public ConsumosController(ConsumosService consumosService) {
        this.consumosService = consumosService;
    }

    @GetMapping
    public ResponseEntity<List<ConsumosResponseDTO>> listConsumos() {
        return ResponseEntity.ok(consumosService.listConsumos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> searchById(@PathVariable Integer id) {
        return ResponseEntity.ok(consumosService.searchById(id));
    }

    @PostMapping
    public ResponseEntity<?> createConsumo(@RequestBody ConsumosRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(consumosService.createConsumos(dto));
    }

    @PutMapping
    public ResponseEntity<?> updateConsumo(@PathVariable Integer id, @RequestBody ConsumosRequestDTO dto) {
        return ResponseEntity.ok((consumosService.updateConsumo(id, dto)));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteConsumo(@PathVariable Integer id) {
        consumosService.deleteConsumo(id);
        return ResponseEntity.noContent().build();
    }
}




