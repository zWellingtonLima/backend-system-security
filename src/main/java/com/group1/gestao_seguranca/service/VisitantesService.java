package com.group1.gestao_seguranca.service;

import com.group1.gestao_seguranca.dto.visitantes.VisitantesRequestDTO;
import com.group1.gestao_seguranca.dto.visitantes.VisitantesResponseDTO;
import com.group1.gestao_seguranca.entity.Visitantes;
import com.group1.gestao_seguranca.repositories.VisitantesRepository;
import com.group1.gestao_seguranca.security.AuthUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VisitantesService {
    private final VisitantesRepository repository;
    private final AuthUtils authUtils;

    public VisitantesService(VisitantesRepository repository, AuthUtils authUtils) {
        this.repository = repository;
        this.authUtils = authUtils;
    }

    // ====================== CREATE ======================
    @Transactional
    public VisitantesResponseDTO criar(VisitantesRequestDTO dto) {
        if (repository.existsByDocumentoIdentificacao(dto.documentoIdentificacao())) {
            throw new IllegalStateException(
                    "Já existe um visitante com o documento: " + dto.documentoIdentificacao());
        }

        Visitantes visitante = new Visitantes(dto.nome(), dto.documentoIdentificacao());
        if (dto.empresa() != null) visitante.setEmpresa(dto.empresa());
        if (dto.observacoes() != null) visitante.setObservacoes(dto.observacoes());

        visitante.setCreateUser(authUtils.getCurrentUserName());

        Visitantes salvo = repository.save(visitante);
        return VisitantesResponseDTO.from(salvo);
    }

    // ====================== READ ======================
    public Page<VisitantesResponseDTO> listarVisitantesAtivos(Pageable pageable) {
        // TODO: Implementar paginação nessas listagens de SELECT *
        return repository.findAllByAtivoTrue(pageable)
                .map(VisitantesResponseDTO::from);
    }

    public VisitantesResponseDTO buscarPorId(int id) {
        Visitantes visitante = repository.findByIdAndAtivo(id)
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

        visitante.setNome(dto.nome());
        visitante.setDocumentoIdentificacao(dto.documentoIdentificacao());
        visitante.setEmpresa(dto.empresa());
        visitante.setObservacoes(dto.observacoes());

        visitante.setModifyUser(authUtils.getCurrentUserName());

        Visitantes atualizado = repository.save(visitante);
        return VisitantesResponseDTO.from(atualizado);
    }

    // ====================== SOFT DELETE ======================
    @Transactional
    public void softDelete(int id) {
        Visitantes visitante = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Visitante com ID " + id + " não encontrado"));

        if (!visitante.isAtivo()) {
            throw new IllegalStateException("Visitante já foi excluído.");
        }

        visitante.desativar();
        visitante.setModifyUser(authUtils.getCurrentUserName());
        repository.save(visitante);
    }
}
