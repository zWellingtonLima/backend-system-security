package com.group1.gestao_seguranca.repositories;

import com.group1.gestao_seguranca.entities.Chaves;
import com.group1.gestao_seguranca.entities.Rondas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RondasRepository extends JpaRepository<Rondas, Integer> {
}
