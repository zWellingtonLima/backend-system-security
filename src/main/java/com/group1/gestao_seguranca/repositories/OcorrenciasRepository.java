package com.group1.gestao_seguranca.repositories;

import com.group1.gestao_seguranca.entities.Ocorrencias;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OcorrenciasRepository extends JpaRepository<Ocorrencias, Integer> {
}
