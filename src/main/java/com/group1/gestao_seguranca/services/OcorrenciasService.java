package com.group1.gestao_seguranca.services;

import com.group1.gestao_seguranca.dto.ocorrencias.OcorrenciasRequestDTO;
import com.group1.gestao_seguranca.dto.ocorrencias.OcorrenciasResponseDTO;
import com.group1.gestao_seguranca.entities.Ocorrencias;
import com.group1.gestao_seguranca.entities.TipoOcorrencia;
import com.group1.gestao_seguranca.entities.Users;
import com.group1.gestao_seguranca.enums.EstadoOcorrenciaEnum;
import com.group1.gestao_seguranca.exceptions.AcessoNegadoException;
import com.group1.gestao_seguranca.repositories.OcorrenciasRepository;
import com.group1.gestao_seguranca.repositories.TipoOcorrenciaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OcorrenciasService {

    private final HttpServletRequest request;
    private final OcorrenciasRepository ocorrenciasRepo;
    private final TipoOcorrenciaRepository tipoOcoRepo;

    public OcorrenciasService(HttpServletRequest request,
                              OcorrenciasRepository ocorrenciasRepo,
                              TipoOcorrenciaRepository tipoOcoRepo) {
        this.request = request;
        this.ocorrenciasRepo = ocorrenciasRepo;
        this.tipoOcoRepo = tipoOcoRepo;
    }

    private Users getUserAutenticado() {
        return (Users) request.getAttribute("usuarioAutenticado");
    }

    // ====================== CREATE ======================
    @Transactional
    public OcorrenciasResponseDTO criar(OcorrenciasRequestDTO dto) {
        Users user = getUserAutenticado();

        TipoOcorrencia tipo = tipoOcoRepo.findByTipoOcorrencia(dto.getTipoOcorrencia())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de ocorrência inválido: " + dto.getTipoOcorrencia()));

        Ocorrencias ocorrencia = new Ocorrencias();
        ocorrencia.setOcorrencia(dto.getOcorrencia());
        ocorrencia.setTipoOcorrencia(tipo);
        ocorrencia.setHoraOcorrencia(dto.getHoraOcorrencia() != null ? dto.getHoraOcorrencia() : LocalDateTime.now());
        ocorrencia.setSeguranca(user);
        ocorrencia.setCreateUser(user.getNomeSeguranca() != null ? user.getNomeSeguranca() : "Sistema");
        ocorrencia.setAtivo(true);

        return OcorrenciasResponseDTO.from(ocorrenciasRepo.save(ocorrencia));
    }

    // ====================== READ ======================
    public List<OcorrenciasResponseDTO> listarTodos() {
        return ocorrenciasRepo.findAll().stream()
                .map(OcorrenciasResponseDTO::from)
                .collect(Collectors.toList());
    }

    public OcorrenciasResponseDTO buscarPorId(Integer id) {
        Ocorrencias ocorrencia = ocorrenciasRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ocorrência com ID " + id + " não encontrada"));

        return OcorrenciasResponseDTO.from(ocorrencia);
    }

    // ====================== UPDATE ======================
    @Transactional
    public OcorrenciasResponseDTO atualizar(Integer id, OcorrenciasRequestDTO dto) {
        Users user = getUserAutenticado();

        Ocorrencias ocorrencia = ocorrenciasRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ocorrência com ID " + id + " não encontrada"));

        if (!ocorrencia.isAtivo()) {
            throw new IllegalStateException("Não é possível atualizar uma ocorrência excluída.");
        }

        if (ocorrencia.getSeguranca().getId() != user.getId()) {
            throw new AcessoNegadoException("Apenas o utilizador que registou pode editar esta ocorrência.");
        }

        if (dto.getOcorrencia() != null) {
            ocorrencia.setOcorrencia(dto.getOcorrencia());
        }
        if (dto.getHoraOcorrencia() != null) {
            ocorrencia.setHoraOcorrencia(dto.getHoraOcorrencia());
        }
        if (dto.getTipoOcorrencia() != null) {
            TipoOcorrencia novoTipo = tipoOcoRepo.findByTipoOcorrencia(dto.getTipoOcorrencia())
                    .orElseThrow(() -> new EntityNotFoundException("Tipo de ocorrência inválido"));
            ocorrencia.setTipoOcorrencia(novoTipo);
        }
        if (dto.getEstado() != null) {
            ocorrencia.setEstado(dto.getEstado());
        }

        ocorrencia.setModifyUser(user.getNomeSeguranca());
        ocorrencia.setModifyDate(LocalDateTime.now());

        return OcorrenciasResponseDTO.from(ocorrenciasRepo.save(ocorrencia));
    }

    // ====================== PATCH ESTADO ======================
    @Transactional
    public OcorrenciasResponseDTO atualizarEstado(Integer id, EstadoOcorrenciaEnum novoEstado) {
        Users user = getUserAutenticado();

        Ocorrencias ocorrencia = ocorrenciasRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ocorrência com ID " + id + " não encontrada"));

        if (ocorrencia.getSeguranca().getId() != user.getId()) {
            throw new AcessoNegadoException("Apenas o utilizador que registou pode alterar o estado.");
        }

        ocorrencia.setEstado(novoEstado);
        ocorrencia.setModifyUser(user.getNomeSeguranca());
        ocorrencia.setModifyDate(LocalDateTime.now());

        return OcorrenciasResponseDTO.from(ocorrenciasRepo.save(ocorrencia));
    }

    // ====================== SOFT DELETE ======================
    @Transactional
    public void softDelete(Integer id) {
        Users user = getUserAutenticado();

        Ocorrencias ocorrencia = ocorrenciasRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ocorrência com ID " + id + " não encontrada"));

        if (!ocorrencia.isAtivo()) {
            throw new IllegalStateException("Ocorrência já foi excluída anteriormente.");
        }

        ocorrencia.setAtivo(false);
        ocorrencia.setDataExclusao(LocalDateTime.now());
        ocorrencia.setModifyUser(user.getNomeSeguranca());
        ocorrencia.setModifyDate(LocalDateTime.now());

        ocorrenciasRepo.save(ocorrencia);
    }
}