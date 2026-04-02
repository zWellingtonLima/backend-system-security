package com.group1.gestao_seguranca.repositories;

import com.group1.gestao_seguranca.entities.EstadoOcorrencia;
import com.group1.gestao_seguranca.entities.TipoOcorrencia;
import com.group1.gestao_seguranca.enums.EstadoOcorrenciaEnum;
import com.group1.gestao_seguranca.enums.TipoOcorrenciaEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoOcorrenciaRepository extends JpaRepository<EstadoOcorrencia, Integer> {
    Optional<EstadoOcorrencia> findByEstadoOcorrencia(EstadoOcorrenciaEnum estadoOcorrencia);
}
