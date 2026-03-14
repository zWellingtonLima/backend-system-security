package com.group1.gestao_seguranca.repositories;

import com.group1.gestao_seguranca.entities.Chaves;
import com.group1.gestao_seguranca.entities.TipoConsumo;
import com.group1.gestao_seguranca.enums.TipoConsumoEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoConsumoRepository extends JpaRepository<TipoConsumo, Integer> {
    Optional<TipoConsumo> findByTipoConsumo(TipoConsumoEnum tipoConsumo);
}
