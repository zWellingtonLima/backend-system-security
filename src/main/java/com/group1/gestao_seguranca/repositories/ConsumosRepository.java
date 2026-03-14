package com.group1.gestao_seguranca.repositories;

import com.group1.gestao_seguranca.entities.Consumos;
import com.group1.gestao_seguranca.entities.TipoConsumo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConsumosRepository extends JpaRepository<Consumos, Integer> {

    List<Consumos> findByAtivoTrue();

    Optional<Consumos> findByIdAndAtivoTrue(Integer id);

    Optional<Consumos> findTopByTipoConsumoAndAtivoTrueOrderByDataRegistoDesc(TipoConsumo tipoConsumo);
}
