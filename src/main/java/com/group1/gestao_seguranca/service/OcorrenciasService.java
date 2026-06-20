package com.group1.gestao_seguranca.service;

import com.group1.gestao_seguranca.dto.ocorrencias.OcorrenciasRequestDTO;
import com.group1.gestao_seguranca.dto.ocorrencias.OcorrenciasResponseDTO;
import com.group1.gestao_seguranca.entity.EstadoOcorrencia;
import com.group1.gestao_seguranca.entity.Ocorrencias;
import com.group1.gestao_seguranca.entity.TipoOcorrencia;
import com.group1.gestao_seguranca.enums.EstadoOcorrenciaEnum;
import com.group1.gestao_seguranca.repositories.EstadoOcorrenciaRepository;
import com.group1.gestao_seguranca.repositories.OcorrenciasRepository;
import com.group1.gestao_seguranca.repositories.TipoOcorrenciaRepository;
import com.group1.gestao_seguranca.security.AuthUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OcorrenciasService {
    private final OcorrenciasRepository ocorrenciasRepo;
    private final TipoOcorrenciaRepository tipoOcorrenciaRepo;
    private final EstadoOcorrenciaRepository estadoOcorrenciaRepo;
    private final AuthUtils authUtils;

    public OcorrenciasService(OcorrenciasRepository ocorrenciasRepo, TipoOcorrenciaRepository tipoOcorrenciaRepo, EstadoOcorrenciaRepository estadoOcorrenciaRepo, AuthUtils authUtils) {
        this.ocorrenciasRepo = ocorrenciasRepo;
        this.tipoOcorrenciaRepo = tipoOcorrenciaRepo;
        this.estadoOcorrenciaRepo = estadoOcorrenciaRepo;
        this.authUtils = authUtils;
    }

    // Listar todas
    @Transactional(readOnly = true)
    public List<OcorrenciasResponseDTO> listarTodos() {
        return ocorrenciasRepo.findAllByOrderByHoraOcorrenciaDesc()
                .stream()
                .map(OcorrenciasResponseDTO::from)
                .toList();
    }

    // Histórico do dia
    @Transactional(readOnly = true)
    public List<OcorrenciasResponseDTO> listarDoDia() {
        LocalDateTime inicioDia = LocalDate.now().atStartOfDay();
        LocalDateTime fimDia = inicioDia.plusDays(1);
        return ocorrenciasRepo
                .findByHoraOcorrenciaBetweenOrderByHoraOcorrenciaDesc(inicioDia, fimDia)
                .stream()
                .map(OcorrenciasResponseDTO::from)
                .toList();
    }

    // ── Buscar por ID ────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public OcorrenciasResponseDTO buscarPorId(Integer id) {
        return OcorrenciasResponseDTO.from(
                ocorrenciasRepo.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Ocorrência não encontrada: id=" + id))
        );
    }

    // ── Criar ───────
    @Transactional
    public OcorrenciasResponseDTO criar(OcorrenciasRequestDTO dto) {
        TipoOcorrencia tipo = tipoOcorrenciaRepo
                .findByTipoOcorrencia(dto.getTipoOcorrencia())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Tipo de ocorrência não encontrado: " + dto.getTipoOcorrencia()));

        EstadoOcorrencia estado = estadoOcorrenciaRepo
                .findByEstadoOcorrencia(EstadoOcorrenciaEnum.PENDENTE)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Estado PENDENTE não encontrado na base de dados"));

        Ocorrencias ocorrencia = new Ocorrencias();
        ocorrencia.setOcorrencia(dto.getOcorrencia());
//        ocorrencia.setTipoOcorrencia(tipo);
//        ocorrencia.setEstadoOcorrencia(estado);
        ocorrencia.setHoraOcorrencia(LocalDateTime.now());
        ocorrencia.setSeguranca(authUtils.getCurrentUser());
        ocorrencia.setCreateUser(authUtils.getCurrentUserName());

        return OcorrenciasResponseDTO.from(ocorrenciasRepo.save(ocorrencia));
    }

    // Atualizar
    @Transactional
    public OcorrenciasResponseDTO atualizar(Integer id, OcorrenciasRequestDTO dto) {
        Ocorrencias ocorrencia = ocorrenciasRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Ocorrência não encontrada: id=" + id));

        if (dto.getTipoOcorrencia() != null) {
            TipoOcorrencia tipo = tipoOcorrenciaRepo
                    .findByTipoOcorrencia(dto.getTipoOcorrencia())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Tipo de ocorrência não encontrado."));
//            ocorrencia.setTipoOcorrencia(tipo);
        }

        if (dto.getEstado() != null) {
            EstadoOcorrencia estado = estadoOcorrenciaRepo
                    .findByEstadoOcorrencia(dto.getEstado())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Estado de ocorrência não encontrado."));
//            ocorrencia.setEstadoOcorrencia(estado);
        }

        if (dto.getOcorrencia() != null)
            ocorrencia.setOcorrencia(dto.getOcorrencia());

        ocorrencia.setModifyUser(authUtils.getCurrentUserName());

        return OcorrenciasResponseDTO.from(ocorrenciasRepo.save(ocorrencia));
    }

    // Atualizar estado
    @Transactional
    public OcorrenciasResponseDTO atualizarEstado(Integer id, EstadoOcorrenciaEnum novoEstado) {
        Ocorrencias ocorrencia = ocorrenciasRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Ocorrência não encontrada: id=" + id));

        EstadoOcorrencia estado = estadoOcorrenciaRepo
                .findByEstadoOcorrencia(novoEstado)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Estado '" + novoEstado + "' não encontrado"));

//        ocorrencia.setEstadoOcorrencia(estado);
        ocorrencia.setModifyUser(authUtils.getCurrentUserName());

        return OcorrenciasResponseDTO.from(ocorrenciasRepo.save(ocorrencia));
    }
}