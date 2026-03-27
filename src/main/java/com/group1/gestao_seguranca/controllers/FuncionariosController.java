package com.group1.gestao_seguranca.controllers;

import com.group1.gestao_seguranca.dto.funcionarios.FuncionariosRequestDTO;
import com.group1.gestao_seguranca.dto.funcionarios.FuncionariosResponseDTO;
import com.group1.gestao_seguranca.services.FuncionariosService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionariosController {

    private final FuncionariosService service;

    public FuncionariosController(FuncionariosService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<FuncionariosResponseDTO> criar(
            @Valid @RequestBody FuncionariosRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<FuncionariosResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionariosResponseDTO> buscarPorId(
            @PathVariable int id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<FuncionariosResponseDTO> buscarPorNumero(
            @PathVariable String numero) {
        return ResponseEntity.ok(service.buscarPorNumero(numero));
    }

    @GetMapping("/setor/{setor}")
    public ResponseEntity<List<FuncionariosResponseDTO>> buscarPorSetor(
            @PathVariable String setor) {
        return ResponseEntity.ok(service.buscarPorSetor(setor));
    }

    // PUT /api/funcionarios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<FuncionariosResponseDTO> atualizar(
            @PathVariable int id,
            @Valid @RequestBody FuncionariosRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }
}