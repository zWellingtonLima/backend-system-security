package com.group1.gestao_seguranca.repositories;

import com.group1.gestao_seguranca.entities.Sessao;
import com.group1.gestao_seguranca.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessaoRepository extends JpaRepository<Sessao, Integer> {
    // O nome do metodo é a forma com que o JPA tem de fazer LIMIT 1 ORDER BY... DESC
    Optional<Sessao> findTopByUserAndHoraSaidaIsNullOrderByCreateDateDesc(Users user);

    Optional<Sessao> findByUserAndHoraSaidaIsNull(Users user);

    Optional<Sessao> findByToken(String token);
}
