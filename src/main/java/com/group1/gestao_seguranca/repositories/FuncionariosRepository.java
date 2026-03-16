package com.group1.gestao_seguranca.repositories;

import com.group1.gestao_seguranca.entities.Funcionarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FuncionariosRepository extends JpaRepository<Funcionarios, Integer> {
    Optional<Funcionarios> findByNumeroFuncionario(String numeroFuncionario);

    boolean existsByNumeroFuncionario(String numeroFuncionario);

    List<Funcionarios> findBySetorIgnoreCase(String setor);
}
