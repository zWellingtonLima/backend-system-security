package com.group1.gestao_seguranca.controllers;

import com.group1.gestao_seguranca.entities.Visitantes;
import com.group1.gestao_seguranca.repositories.VisitantesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


    @RestController
    @RequestMapping("/api/visitantes")
    public class VisitantesControler {

        @Autowired
        VisitantesRepository visitantesRepo;

        @GetMapping
        public List<Visitantes> getVisitantes() {
            return visitantesRepo.findAll();
        }

        @PostMapping
        public Boolean createVisitante(@RequestBody Visitantes visitantes){
            visitantesRepo.save(visitantes);

            return true;
        }
    }

