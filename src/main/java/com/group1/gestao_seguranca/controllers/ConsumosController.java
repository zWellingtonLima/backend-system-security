package com.group1.gestao_seguranca.controllers;

import com.group1.gestao_seguranca.entities.Consumos;
import com.group1.gestao_seguranca.repositories.ConsumosRepository;
import com.group1.gestao_seguranca.repositories.TipoConsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consumos")
public class ConsumosController {

    @Autowired
    private ConsumosRepository consumosRepo;

    @Autowired
    private TipoConsumoRepository tipoConsumoRepo;


    @GetMapping
    public List<Consumos> listar() {
        return consumosRepo.findAll();
    }
    @PostMapping
    public Consumos create(@RequestBody Consumos consumo) {
        return consumosRepo.save(consumo);
    }
    @GetMapping("/{id}")
    public Consumos search(@PathVariable Integer id) {
        return consumosRepo.findById(id).orElse(null);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        consumosRepo.deleteById(id);
    }
}




