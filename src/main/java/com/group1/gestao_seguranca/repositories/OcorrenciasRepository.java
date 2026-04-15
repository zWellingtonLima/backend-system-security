package com.group1.gestao_seguranca.repositories;

import com.group1.gestao_seguranca.entities.Ocorrencias;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OcorrenciasRepository extends JpaRepository<Ocorrencias, Integer> {

    // Histórico completo ordenado por data
    List<Ocorrencias> findAllByOrderByHoraOcorrenciaDesc();

    // Histórico do dia (ocorrências entre início e fim do dia)
    List<Ocorrencias> findByHoraOcorrenciaBetweenOrderByHoraOcorrenciaDesc(
            LocalDateTime inicio,
            LocalDateTime fim
    );
}