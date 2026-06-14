package com.group1.gestao_seguranca.repositories;

import com.group1.gestao_seguranca.entity.Sessao;
import com.group1.gestao_seguranca.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SessaoRepository extends JpaRepository<Sessao, Integer> {
    // O nome do metodo é a forma com que o JPA tem de fazer LIMIT 1 ORDER BY... DESC
    Optional<Sessao> findTopByUserAndHoraSaidaIsNullOrderByCreateDateDesc(User user);

    // O User dentro da Sessao esta como Lazy entao JPA nao carrega a tempo
    @Query("SELECT s FROM Sessao s JOIN FETCH s.user WHERE s.token = :token")
    Optional<Sessao> findByToken(@Param("token") String token);
}
