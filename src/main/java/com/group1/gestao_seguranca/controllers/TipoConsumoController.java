package com.group1.gestao_seguranca.controllers;

import com.group1.gestao_seguranca.entities.TipoConsumo;
import com.group1.gestao_seguranca.repositories.TipoConsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/api/tipo-consumo")
    public class TipoConsumoController {

        @Autowired
        private TipoConsumoRepository tipoConsumoRepo;

        @GetMapping
        public List<TipoConsumo> list() {
            return tipoConsumoRepo.findAll();
        }

        @PostMapping
        public TipoConsumo create(@RequestBody TipoConsumo tipo) {
            return tipoConsumoRepo.save(tipo);
        }

        @GetMapping("/{id}")
        public TipoConsumo search(@PathVariable Integer id) {
            return tipoConsumoRepo.findById(id).orElse(null);
        }

        @DeleteMapping("/{id}")
        public void delete(@PathVariable Integer id) {
            tipoConsumoRepo.deleteById(id);
        }
    }

