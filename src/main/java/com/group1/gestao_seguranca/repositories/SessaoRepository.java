package com.group1.gestao_seguranca.repositories;

import com.group1.gestao_seguranca.entities.Sessao;
import com.group1.gestao_seguranca.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessaoRepository extends JpaRepository<Sessao, Integer> {
    Optional<Sessao> findByUserAndHoraSaidaIsNull(Users user);
}
