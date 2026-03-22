package com.group1.gestao_seguranca.controllers;

import com.group1.gestao_seguranca.dto.movimentacoes.DevolucaoResponseDTO;
import com.group1.gestao_seguranca.dto.movimentacoes.EntregaChaveDTO;
import com.group1.gestao_seguranca.dto.movimentacoes.MovimentacaoRequestDTO;
import com.group1.gestao_seguranca.dto.movimentacoes.MovimentacaoResponseDTO;
import com.group1.gestao_seguranca.services.MovimentacoesService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movimentacoes")
public class MovimentacoesController {

    private final MovimentacoesService service;

    public MovimentacoesController(MovimentacoesService service) {
        this.service = service;
    }

    @PostMapping("/entrada")
    public ResponseEntity<MovimentacaoResponseDTO> registrarEntrada(@Valid @RequestBody MovimentacaoRequestDTO dto) {
        validarRequest(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarEntrada(dto));
    }

    @PatchMapping("/saida/{id}")
    public ResponseEntity<?> registrarSaida(@PathVariable int id) {
        MovimentacaoResponseDTO response = service.registrarSaida(id);
        return ResponseEntity.ok(response);
    }

    @Validated
    @PatchMapping("/devolucao/{idEntrega}")
    public ResponseEntity<DevolucaoResponseDTO> registrarDevolucao(
            @PathVariable int idEntrega,
            @NotBlank(message = "O campo \"devolvidaPor\" é obrigatório")
            @RequestParam String devolvidaPor) {
        return ResponseEntity.ok(service.registrarDevolucao(idEntrega, devolvidaPor));
    }

    // ---------
    // Metodo apoio
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

        EntregaChaveDTO entrega = dto.getEntregaChave();
        if (entrega != null && entrega.getIdChave() == null)
            throw new IllegalArgumentException("Entrega de chave requer a identificação da Chave.");

    }
}
