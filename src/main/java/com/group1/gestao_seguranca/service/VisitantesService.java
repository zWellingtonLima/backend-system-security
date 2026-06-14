package com.group1.gestao_seguranca.service;

import com.group1.gestao_seguranca.dto.visitantes.VisitantesRequestDTO;
import com.group1.gestao_seguranca.dto.visitantes.VisitantesResponseDTO;
import com.group1.gestao_seguranca.entity.User;
import com.group1.gestao_seguranca.entity.Visitantes;
import com.group1.gestao_seguranca.repositories.VisitantesRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitantesService {
    private final HttpServletRequest request;
    private final VisitantesRepository repository;

    public VisitantesService(HttpServletRequest request, VisitantesRepository repository) {
        this.request = request;
        this.repository = repository;
    }

    // ====================== CREATE ======================
    @Transactional
    public VisitantesResponseDTO criar(VisitantesRequestDTO dto) {
        if (repository.existsByDocumentoIdentificacao(dto.getDocumentoIdentificacao())) {
            throw new IllegalStateException(
                    "Já existe um visitante com o documento: " + dto.getDocumentoIdentificacao());
        }

        User user = getUserAutenticado();

        Visitantes visitante = new Visitantes();
        visitante.setNomeVisitante(dto.getNomeVisitante());
        visitante.setDocumentoIdentificacao(dto.getDocumentoIdentificacao());
        visitante.setEmpresa(dto.getEmpresa());
        visitante.setObservacoes(dto.getObservacoes() != null ? dto.getObservacoes() : "");
        visitante.setCreateUser(user.getNomeSeguranca());

        Visitantes salvo = repository.save(visitante);
        return VisitantesResponseDTO.from(salvo);
    }

    // ====================== READ ======================
    public List<VisitantesResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(VisitantesResponseDTO::from)
                .collect(Collectors.toList());
    }

    public VisitantesResponseDTO buscarPorId(int id) {
        Visitantes visitante = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Visitante com ID " + id + " não encontrado"));

        return VisitantesResponseDTO.from(visitante);
    }

    public VisitantesResponseDTO buscarPorDocumento(String documento) {
        Visitantes visitante = repository.findByDocumentoIdentificacao(documento)
                .orElseThrow(() -> new EntityNotFoundException("Visitante com documento " + documento + " não encontrado"));

        return VisitantesResponseDTO.from(visitante);
    }

    // ====================== UPDATE ======================
    @Transactional
    public VisitantesResponseDTO atualizar(int id, VisitantesRequestDTO dto) {
        Visitantes visitante = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Visitante com ID " + id + " não encontrado"));

        if (!visitante.isAtivo()) {
            throw new IllegalStateException("Não é possível atualizar um visitante excluído.");
        }

        User user = getUserAutenticado();

        visitante.setNomeVisitante(dto.getNomeVisitante());
        visitante.setDocumentoIdentificacao(dto.getDocumentoIdentificacao());
        visitante.setEmpresa(dto.getEmpresa());
        visitante.setObservacoes(dto.getObservacoes());
        visitante.setModifyUser(user.getNomeSeguranca());

        Visitantes atualizado = repository.save(visitante);
        return VisitantesResponseDTO.from(atualizado);
    }

    // ====================== SOFT DELETE ======================
    @Transactional
    public void softDelete(int id) {
        Visitantes visitante = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Visitante com ID " + id + " não encontrado"));

        if (!visitante.isAtivo()) {
            throw new IllegalStateException("Visitante já foi excluído anteriormente.");
        }

        User user = getUserAutenticado();

        visitante.setAtivo(false);
        visitante.setDataExclusao(LocalDateTime.now());
        visitante.setModifyUser(user.getNomeSeguranca());
        repository.save(visitante);
    }

    private User getUserAutenticado() {
        return (User) request.getAttribute("usuarioAutenticado");
    }
}
