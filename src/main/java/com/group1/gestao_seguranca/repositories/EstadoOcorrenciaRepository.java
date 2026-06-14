package com.group1.gestao_seguranca.repositories;

import com.group1.gestao_seguranca.entity.EstadoOcorrencia;
import com.group1.gestao_seguranca.enums.EstadoOcorrenciaEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoOcorrenciaRepository extends JpaRepository<EstadoOcorrencia, Integer> {
    Optional<EstadoOcorrencia> findByEstadoOcorrencia(EstadoOcorrenciaEnum estadoOcorrencia);
}
