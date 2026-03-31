package com.group1.gestao_seguranca.repositories;

import com.group1.gestao_seguranca.entities.Funcionarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionariosRepository extends JpaRepository<Funcionarios, Integer> {

    // Busca por número de funcionário (único)
    Optional<Funcionarios> findByNumeroFuncionario(String numeroFuncionario);

    // Verifica se já existe um número de funcionário
    boolean existsByNumeroFuncionario(String numeroFuncionario);

    // Busca por setor (case-insensitive)
    List<Funcionarios> findBySetorIgnoreCase(String setor);

    // Busca simples por setor (mantida para compatibilidade)
    List<Funcionarios> findBySetor(String setor);

    // Busca avançada por nome (suporta até 3 palavras/chunks)
    @Query("""
            SELECT f FROM Funcionarios f 
            WHERE LOWER(f.nomeFuncionario) LIKE LOWER(CONCAT('%', :t1, '%'))
              AND LOWER(f.nomeFuncionario) LIKE LOWER(CONCAT('%', :t2, '%'))
              AND LOWER(f.nomeFuncionario) LIKE LOWER(CONCAT('%', :t3, '%'))
            ORDER BY f.nomeFuncionario ASC
           """)
    List<Funcionarios> procurarNomeFuncionario(
            @Param("t1") String t1,
            @Param("t2") String t2,
            @Param("t3") String t3
    );

    // ====================== SOFT DELETE ======================

    // Verifica se o funcionário existe e está ativo
    boolean existsByIdAndAtivo(Integer id, boolean ativo);

    // Busca apenas funcionários ativos (útil em alguns casos)
    List<Funcionarios> findAllByAtivoTrue();
}