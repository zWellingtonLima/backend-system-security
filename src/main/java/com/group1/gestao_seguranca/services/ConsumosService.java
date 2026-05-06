package com.group1.gestao_seguranca.services;

import com.group1.gestao_seguranca.dto.consumos.ConsumosRequestDTO;
import com.group1.gestao_seguranca.dto.consumos.ConsumosResponseDTO;
import com.group1.gestao_seguranca.dto.consumos.ConsumosUltimasLeiturasDTO;
import com.group1.gestao_seguranca.entities.Consumos;
import com.group1.gestao_seguranca.entities.TipoConsumo;
import com.group1.gestao_seguranca.entities.Users;
import com.group1.gestao_seguranca.enums.TipoConsumoEnum;
import com.group1.gestao_seguranca.repositories.ConsumosRepository;
import com.group1.gestao_seguranca.repositories.TipoConsumoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
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
    private final HttpServletRequest request;

    public ConsumosService(HttpServletRequest request,
                           ConsumosRepository consumosRepo,
                           TipoConsumoRepository tipoConsumoRepo) {
        this.request = request;
        this.consumosRepo = consumosRepo;
        this.tipoConsumoRepo = tipoConsumoRepo;
    }

    private Users getUserAutenticado() {
        return (Users) request.getAttribute("usuarioAutenticado");
    }

    // Cálculo puro — não faz I/O. O caller fornece o anterior (pode ser null).
    private Integer calcularConsumo(Consumos consumo, Consumos anterior) {
        if (anterior == null) return null;
        return consumo.getValorLeitura() - anterior.getValorLeitura();
    }

    private Consumos buscarAnterior(Consumos consumo) {
        return consumosRepo.findAnteriorByTipo(
                consumo.getTipoConsumo().getTipoConsumo(),
                consumo.getDataRegisto()
        ).orElse(null);
    }

    // ====================== READ ======================
    public List<ConsumosResponseDTO> listConsumos() {
        // Lista vem ordenada por dataRegisto DESC, com JOIN FETCH em tipoConsumo (1 query).
        List<Consumos> ativos = consumosRepo.findByAtivoTrue();

        // Para cada tipo, dentro do grupo (DESC) o "anterior" cronológico é o item seguinte na lista.
        Map<TipoConsumoEnum, List<Consumos>> porTipo = ativos.stream()
                .collect(Collectors.groupingBy(c -> c.getTipoConsumo().getTipoConsumo()));

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
                        c -> c.getTipoConsumo().getTipoConsumo(),
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
        Users user = getUserAutenticado();

        TipoConsumo tipoConsumo = tipoConsumoRepo.findByTipoConsumo(dto.getTipoConsumo())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de consumo não encontrado"));

        Consumos consumo = new Consumos();
        consumo.setValorLeitura(dto.getValorLeitura());
        consumo.setDataRegisto(LocalDateTime.now());
        consumo.setObservacao(dto.getObservacao());
        consumo.setTipoConsumo(tipoConsumo);
        consumo.setUser(user);
        consumo.setCreateUser(user.getNomeSeguranca() != null ? user.getNomeSeguranca() : "Sistema");
        consumo.setAtivo(true);

        consumosRepo.save(consumo);
        return ConsumosResponseDTO.from(consumo, calcularConsumo(consumo, buscarAnterior(consumo)));
    }

    // ====================== UPDATE (Todos os campos) ======================
    @Transactional
    public ConsumosResponseDTO updateConsumo(Integer id, ConsumosRequestDTO dto) {
        Users user = getUserAutenticado();

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
            consumo.setObservacao(dto.getObservacao());
        }
        if (dto.getTipoConsumo() != null) {
            TipoConsumo novoTipo = tipoConsumoRepo.findByTipoConsumo(dto.getTipoConsumo())
                    .orElseThrow(() -> new EntityNotFoundException("Tipo de consumo não encontrado"));
            consumo.setTipoConsumo(novoTipo);
        }
        if (dto.getDataRegisto() != null) {
            consumo.setDataRegisto(dto.getDataRegisto());
        }

        // Auditoria
        consumo.setModifyUser(user.getNomeSeguranca() != null ? user.getNomeSeguranca() : "Sistema");
        consumo.setModifyDate(LocalDateTime.now());

        consumosRepo.save(consumo);

        return ConsumosResponseDTO.from(consumo, calcularConsumo(consumo, buscarAnterior(consumo)));
    }

    // ====================== SOFT DELETE ======================
    @Transactional
    public void deleteConsumo(Integer id) {
        Users user = getUserAutenticado();

        Consumos consumo = consumosRepo.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Consumo não encontrado ou já eliminado."));

        consumo.setAtivo(false);
        consumo.setModifyUser(user.getNomeSeguranca() != null ? user.getNomeSeguranca() : "Sistema");
        consumo.setModifyDate(LocalDateTime.now());

        consumosRepo.save(consumo);
    }
}
