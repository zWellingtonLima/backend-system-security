package com.group1.gestao_seguranca.repositories;

import com.group1.gestao_seguranca.entities.Ocorrencias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.group1.gestao_seguranca.entities.Ocorrencias;
import com.group1.gestao_seguranca.entities.Users;
import java.util.List;
import java.util.Optional;

public interface OcorrenciasRepository extends JpaRepository<Ocorrencias, Integer> {
    List<Ocorrencias> findBySeguranca(Users seguranca);
    Optional<Ocorrencias> findByIdAndSeguranca(Integer id, Users seguranca);
}