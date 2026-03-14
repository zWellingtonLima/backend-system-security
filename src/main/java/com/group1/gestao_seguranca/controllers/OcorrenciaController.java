package com.group1.gestao_seguranca.controllers;

import com.group1.gestao_seguranca.entities.*;
import com.group1.gestao_seguranca.repositories.OcorrenciasRepository;
import com.group1.gestao_seguranca.repositories.UsersRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ocorrencias")
public class OcorrenciaController {

    private final OcorrenciasRepository ocorrenciasRepo;
    private final UsersRepository usersRepo;

    public OcorrenciaController(OcorrenciasRepository ocorrenciasRepo, UsersRepository usersRepo) {
        this.ocorrenciasRepo = ocorrenciasRepo;
        this.usersRepo = usersRepo;
    }

    @GetMapping
    public List<Ocorrencias> list() {
        return ocorrenciasRepo.findAll();
    }

    @PostMapping
    public Ocorrencias create(@RequestBody Ocorrencias ocorrencias) {
        Users user = usersRepo.findById(ocorrencias.getSeguranca().getId())
                .orElseThrow();
        ocorrencias.setSeguranca(user);
        return ocorrenciasRepo.save(ocorrencias);
    }
}
