package com.group1.gestao_seguranca.repositories;

import com.group1.gestao_seguranca.entity.Consumos;
import com.group1.gestao_seguranca.enums.TipoConsumoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ConsumosRepository extends JpaRepository<Consumos, Integer> {

    @Query("""
            SELECT c FROM Consumos c
            JOIN FETCH c.tipoConsumo
            WHERE c.ativo = true
            ORDER BY c.dataRegisto DESC
            """)
    List<Consumos> findByAtivoTrue();

    Optional<Consumos> findByIdAndAtivoTrue(Integer id);

    @Query("SELECT c FROM Consumos c WHERE c.tipoConsumo.tipo = :tipo AND c.ativo = true ORDER BY c.dataRegisto DESC LIMIT 1")
    Optional<Consumos> findUltimaLeituraByTipo(@Param("tipo") TipoConsumoEnum tipo);

    @Query("""
            SELECT c FROM Consumos c
            JOIN FETCH c.tipoConsumo t
            WHERE c.ativo = true
              AND c.dataRegisto = (
                  SELECT MAX(c2.dataRegisto) FROM Consumos c2
                  WHERE c2.ativo = true AND c2.tipoConsumo = c.tipoConsumo
              )
            """)
    List<Consumos> findUltimasLeiturasPorTipo();

    @Query("SELECT c FROM Consumos c WHERE c.tipoConsumo.tipo = :tipo AND c.ativo = true AND c.dataRegisto < :data ORDER BY c.dataRegisto DESC LIMIT 1")
    Optional<Consumos> findAnteriorByTipo(
            @Param("tipo") TipoConsumoEnum tipo,
            @Param("data") LocalDateTime data
    );
}
