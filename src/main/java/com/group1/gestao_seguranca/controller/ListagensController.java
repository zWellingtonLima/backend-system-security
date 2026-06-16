package com.group1.gestao_seguranca.controller;

import com.group1.gestao_seguranca.dto.movimentacoes.MovimentacaoResponseDTO;
import com.group1.gestao_seguranca.enums.TipoChaveEnum;
import com.group1.gestao_seguranca.enums.TipoConsumoEnum;
import com.group1.gestao_seguranca.enums.TipoOcorrenciaEnum;
import com.group1.gestao_seguranca.service.MovimentacoesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/lists/")
public class ListagensController {
    private final MovimentacoesService service;

    public ListagensController(MovimentacoesService service) {
        this.service = service;
    }

    @GetMapping("/tipos-consumo")
    public ResponseEntity<?> getTiposConsumo() {
        return ResponseEntity.ok(TipoConsumoEnum.values());
    }

    @GetMapping("/tipos-chave")
    public ResponseEntity<?> getTiposChave() {
        return ResponseEntity.ok(TipoChaveEnum.values());
    }

    @GetMapping("/tipos-ocorrencia")
    public ResponseEntity<?> getTiposOcorrencia() {
        return ResponseEntity.ok(TipoOcorrenciaEnum.values());
    }

    // Listagens Movimentacoes
    // Tela principal do segurança — quem está dentro agora
    @GetMapping("/movimentacoes/ativas")
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarAtivas() {
        return ResponseEntity.ok(service.listarAtivas());
    }

    // Histórico completo
    @GetMapping("/movimentacoes/todas")
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    // Histórico de um funcionário específico
    @GetMapping("movimentacoes/funcionario/{id}")
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarPorFuncionario(@PathVariable int id) {
        return ResponseEntity.ok(service.listarPorFuncionario(id));
    }

    // Histórico de um visitante específico
    @GetMapping("movimentacoes/visitante/{id}")
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarPorVisitante(@PathVariable int id) {
        return ResponseEntity.ok(service.listarPorVisitante(id));
    }
}
