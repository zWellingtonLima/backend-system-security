package com.group1.gestao_seguranca.repositories;

import com.group1.gestao_seguranca.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Integer> {
    Optional<User> findByNumeroIdentificacao(String numeroIdentificacao);
}
