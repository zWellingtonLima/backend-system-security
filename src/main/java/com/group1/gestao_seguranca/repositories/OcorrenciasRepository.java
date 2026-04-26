package com.group1.gestao_seguranca.repositories;

import com.group1.gestao_seguranca.entities.Ocorrencias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OcorrenciasRepository extends JpaRepository<Ocorrencias, Integer> {

    @Query("""
            SELECT o FROM Ocorrencias o
            JOIN FETCH o.tipoOcorrencia
            JOIN FETCH o.estadoOcorrencia
            ORDER BY o.horaOcorrencia DESC
            """)
    List<Ocorrencias> findAllByOrderByHoraOcorrenciaDesc();

    @Query("""
            SELECT o FROM Ocorrencias o
            JOIN FETCH o.tipoOcorrencia
            JOIN FETCH o.estadoOcorrencia
            WHERE o.horaOcorrencia BETWEEN :inicio AND :fim
            ORDER BY o.horaOcorrencia DESC
            """)
    List<Ocorrencias> findByHoraOcorrenciaBetweenOrderByHoraOcorrenciaDesc(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );
}