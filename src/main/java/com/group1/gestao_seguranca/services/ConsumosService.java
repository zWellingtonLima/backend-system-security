package com.group1.gestao_seguranca.services;

import com.group1.gestao_seguranca.dto.consumos.ConsumosRequestDTO;
import com.group1.gestao_seguranca.dto.consumos.ConsumosResponseDTO;
import com.group1.gestao_seguranca.entities.Consumos;
import com.group1.gestao_seguranca.entities.TipoConsumo;
import com.group1.gestao_seguranca.entities.Users;
import com.group1.gestao_seguranca.repositories.ConsumosRepository;
import com.group1.gestao_seguranca.repositories.TipoConsumoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ConsumosService {
    private final ConsumosRepository consumosRepo;
    private final TipoConsumoRepository tipoConsumoRepo;
    private final HttpServletRequest request;

    // Ess e um metodo auxilar para evitar usar a linha abaixo em todos os servicos
    private Users getUserAutenticado() {
        return (Users) request.getAttribute("usuarioAutenticado");
    }

    public ConsumosService(HttpServletRequest request, ConsumosRepository consumosRepo, TipoConsumoRepository tipoConsumoRepo) {
        this.request = request;
        this.consumosRepo = consumosRepo;
        this.tipoConsumoRepo = tipoConsumoRepo;
    }

    public List<ConsumosResponseDTO> listConsumos() {
        return consumosRepo.findAll()
                .stream()
                .map(ConsumosResponseDTO::from)
                .toList();
    }

    public ConsumosResponseDTO searchById(Integer id) {
        return consumosRepo.findById(id)
                .map(ConsumosResponseDTO::from)
                .orElseThrow(() -> new RuntimeException("Consumo não encontrado: " + id));
    }

    public ConsumosResponseDTO createConsumos(ConsumosRequestDTO dto) {
        Users user = getUserAutenticado();

        TipoConsumo tipoConsumo = tipoConsumoRepo.findByTipoConsumo(dto.getTipoConsumo())
                .orElseThrow(() -> new RuntimeException("Tipo de consumo não encontrado."));

        Optional<Consumos> ultimaLeitura = consumosRepo
                .findTopByTipoConsumoAndAtivoTrueOrderByDataRegistoDesc(tipoConsumo);

        Consumos consumo = new Consumos();
        consumo.setValorLeitura(dto.getValorLeitura());
        consumo.setDataRegisto(LocalDateTime.now());
        consumo.setObservacao(dto.getObservacao());
        consumo.setTipoConsumo(tipoConsumo);
        consumo.setUser(user);
        consumo.setCreateUser(dto.getCreateUser());
        consumosRepo.save(consumo);

        Integer consumoCalculado = ultimaLeitura
                .map(ultima -> consumo.getValorLeitura() - ultima.getValorLeitura())
                .orElse(null);

        return ConsumosResponseDTO.from(consumo, consumoCalculado);
    }

    public ConsumosResponseDTO updateConsumo(Integer id, ConsumosRequestDTO dto) {
        Users user = getUserAutenticado();

        Consumos consumo = consumosRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Consumo não encontrado."));
        TipoConsumo tipoConsumo = tipoConsumoRepo.findByTipoConsumo(dto.getTipoConsumo())
                .orElseThrow(() -> new RuntimeException("Tipo de consumo não encontrado."));

        consumo.setValorLeitura(dto.getValorLeitura());
        consumo.setObservacao(dto.getObservacao());
        consumo.setTipoConsumo(tipoConsumo);
        consumo.setModifyUser(user.getNomeSeguranca());
        consumosRepo.save(consumo);

        return ConsumosResponseDTO.from(consumo);
    }

    public void deleteConsumo(Integer id) {
        Users user = getUserAutenticado();

        Consumos consumo = consumosRepo.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Consumo não encontrado ou já eliminado."));

        // Soft delete, ou seja, marcamos como inativo em vez de apagar
        // e auditoria para verificar quem `apagou` e quando.
        consumo.setAtivo(false);
        consumo.setModifyUser(user.getNomeSeguranca());
        consumosRepo.save(consumo);
    }
}
