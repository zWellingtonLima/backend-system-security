package com.group1.gestao_seguranca.service;

import com.group1.gestao_seguranca.dto.consumos.ConsumosRequestDTO;
import com.group1.gestao_seguranca.dto.consumos.ConsumosResponseDTO;
import com.group1.gestao_seguranca.dto.consumos.ConsumosUltimasLeiturasDTO;
import com.group1.gestao_seguranca.entity.Consumos;
import com.group1.gestao_seguranca.entity.TipoConsumo;
import com.group1.gestao_seguranca.enums.TipoConsumoEnum;
import com.group1.gestao_seguranca.repositories.ConsumosRepository;
import com.group1.gestao_seguranca.repositories.TipoConsumoRepository;
import com.group1.gestao_seguranca.security.AuthUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConsumosService {

    private final ConsumosRepository consumosRepo;
    private final TipoConsumoRepository tipoConsumoRepo;
    private final AuthUtils authUtils;

    public ConsumosService(ConsumosRepository consumosRepo, TipoConsumoRepository tipoConsumoRepo, AuthUtils authUtils) {
        this.consumosRepo = consumosRepo;
        this.tipoConsumoRepo = tipoConsumoRepo;
        this.authUtils = authUtils;
    }

    // Cálculo puro — não faz I/O. O caller fornece o anterior (pode ser null).
    private Integer calcularConsumo(Consumos consumo, Consumos anterior) {
        if (anterior == null) return null;
        return consumo.getValorLeitura() - anterior.getValorLeitura();
    }

    private Consumos buscarAnterior(Consumos consumo) {
        return consumosRepo.findAnteriorByTipo(
                consumo.getTipoConsumo(),
                consumo.getDataRegisto()
        ).orElse(null);
    }

    // ====================== READ ======================
    public List<ConsumosResponseDTO> listConsumos() {
        // Lista vem ordenada por dataRegisto DESC, com JOIN FETCH em tipoConsumo (1 query).
        List<Consumos> ativos = consumosRepo.findByAtivoTrue();

        // Para cada tipo, dentro do grupo (DESC) o "anterior" cronológico é o item seguinte na lista.
        Map<TipoConsumoEnum, List<Consumos>> porTipo = ativos.stream()
                .collect(Collectors.groupingBy(c -> c.getTipoConsumo()));

        Map<Integer, Consumos> anteriorPorId = new HashMap<>();
        for (List<Consumos> grupo : porTipo.values()) {
            for (int i = 0; i < grupo.size() - 1; i++) {
                anteriorPorId.put(grupo.get(i).getId(), grupo.get(i + 1));
            }
        }

        List<ConsumosResponseDTO> resultado = new ArrayList<>(ativos.size());
        for (Consumos c : ativos) {
            resultado.add(ConsumosResponseDTO.from(c, calcularConsumo(c, anteriorPorId.get(c.getId()))));
        }
        return resultado;
    }

    public ConsumosUltimasLeiturasDTO getUltimasLeituras() {
        Map<TipoConsumoEnum, Integer> leiturasPorTipo = consumosRepo.findUltimasLeiturasPorTipo()
                .stream()
                .collect(Collectors.toMap(
                        Consumos::getTipoConsumo,
                        Consumos::getValorLeitura
                ));

        return new ConsumosUltimasLeiturasDTO(
                leiturasPorTipo.get(TipoConsumoEnum.AGUA),
                leiturasPorTipo.get(TipoConsumoEnum.ELETRICIDADE),
                leiturasPorTipo.get(TipoConsumoEnum.GAS)
        );
    }

    public ConsumosResponseDTO searchById(Integer id) {
        Consumos consumo = consumosRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consumo com ID " + id + " não encontrado"));

        return ConsumosResponseDTO.from(consumo, calcularConsumo(consumo, buscarAnterior(consumo)));
    }

    // ====================== CREATE ======================
    @Transactional
    public ConsumosResponseDTO createConsumos(ConsumosRequestDTO dto) {
        TipoConsumo tipoConsumo = tipoConsumoRepo.findByTipoConsumo(dto.getTipoConsumo())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de consumo não encontrado"));

        Consumos consumo = new Consumos();
        consumo.setValorLeitura(dto.getValorLeitura());
        consumo.setDataRegisto(LocalDateTime.now());
        consumo.setObservacoes(dto.getObservacao());
//        consumo.setTipoConsumo(tipoConsumo);
        consumo.setUser(authUtils.getCurrentUser());
        consumo.setCreateUser(authUtils.getCurrentUserName());

        consumosRepo.save(consumo);
        return ConsumosResponseDTO.from(consumo, calcularConsumo(consumo, buscarAnterior(consumo)));
    }

    // ====================== UPDATE (Todos os campos) ======================
    @Transactional
    public ConsumosResponseDTO updateConsumo(Integer id, ConsumosRequestDTO dto) {
        Consumos consumo = consumosRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consumo com ID " + id + " não encontrado"));

        if (!consumo.isAtivo()) {
            throw new IllegalStateException("Não é possível atualizar um consumo excluído.");
        }

        // Atualização de todos os campos permitidos
        if (dto.getValorLeitura() != null) {
            consumo.setValorLeitura(dto.getValorLeitura());
        }
        if (dto.getObservacao() != null) {
            consumo.setObservacoes(dto.getObservacao());
        }
        if (dto.getTipoConsumo() != null) {
            TipoConsumo novoTipo = tipoConsumoRepo.findByTipoConsumo(dto.getTipoConsumo())
                    .orElseThrow(() -> new EntityNotFoundException("Tipo de consumo não encontrado"));
//            consumo.setTipoConsumo(novoTipo);
        }
        if (dto.getDataRegisto() != null) {
            consumo.setDataRegisto(dto.getDataRegisto());
        }

        // Auditoria
        consumo.setModifyUser(authUtils.getCurrentUserName());

        consumosRepo.save(consumo);

        return ConsumosResponseDTO.from(consumo, calcularConsumo(consumo, buscarAnterior(consumo)));
    }

    // ====================== SOFT DELETE ======================
    @Transactional
    public void deleteConsumo(Integer id) {
        Consumos consumo = consumosRepo.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Consumo não encontrado ou já eliminado."));

        consumo.desativar();
        consumo.setModifyUser(authUtils.getCurrentUserName());

        consumosRepo.save(consumo);
    }
}
