package com.group1.gestao_seguranca.services;

import com.group1.gestao_seguranca.dto.ocorrencias.OcorrenciasRequestDTO;
import com.group1.gestao_seguranca.dto.ocorrencias.OcorrenciasResponseDTO;
import com.group1.gestao_seguranca.entities.Ocorrencias;
import com.group1.gestao_seguranca.entities.TipoOcorrencia;
import com.group1.gestao_seguranca.entities.Users;
import com.group1.gestao_seguranca.repositories.OcorrenciasRepository;
import com.group1.gestao_seguranca.repositories.TipoOcorrenciaRepository;
import com.group1.gestao_seguranca.repositories.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OcorrenciasService {

    private final HttpServletRequest request;
    private final OcorrenciasRepository ocorrenciasRepo;
    private final UsersRepository usersRepo;
    private final TipoOcorrenciaRepository tipoOcoRepo;

    public OcorrenciasService(HttpServletRequest request, OcorrenciasRepository ocorrenciasRepo, UsersRepository usersRepo, TipoOcorrenciaRepository tipoOcoRepo) {
        this.request = request;
        this.ocorrenciasRepo = ocorrenciasRepo;
        this.usersRepo = usersRepo;
        this.tipoOcoRepo = tipoOcoRepo;
    }

    private Users getUserAutenticado() {
        return (Users) request.getAttribute("usuarioAutenticado");
    }

    public List<OcorrenciasResponseDTO> listarTodos() {
        return ocorrenciasRepo.findAll()
                .stream()
                .map(OcorrenciasResponseDTO::from)
                .toList();
    }

    @Transactional
    public OcorrenciasResponseDTO criar(OcorrenciasRequestDTO dto) {
        Users user = getUserAutenticado();

        TipoOcorrencia tipo = tipoOcoRepo
                .findByTipoOcorrencia(dto.getTipoOcorrencia())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de ocorrência é inválido"));

        Ocorrencias ocorrencia = new Ocorrencias();
        ocorrencia.setOcorrencia(dto.getOcorrencia());
        ocorrencia.setTipoOcorrencia(tipo);
        ocorrencia.setHoraOcorrencia(LocalDateTime.now());
        ocorrencia.setSeguranca(user);
        ocorrencia.setCreateUser(user.getNomeSeguranca());

        return OcorrenciasResponseDTO.from(ocorrenciasRepo.save(ocorrencia));
    }
}
