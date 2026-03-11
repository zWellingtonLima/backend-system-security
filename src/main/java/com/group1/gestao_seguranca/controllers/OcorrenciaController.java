package com.group1.gestao_seguranca.controllers;

import com.group1.gestao_seguranca.entities.Consumos;
import com.group1.gestao_seguranca.entities.Ocorrencias;
import com.group1.gestao_seguranca.entities.TipoConsumo;
import com.group1.gestao_seguranca.entities.Users;
import com.group1.gestao_seguranca.repositories.OcorrenciasRepository;
import com.group1.gestao_seguranca.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ocorrencias")
public class OcorrenciaController {

    @Autowired private OcorrenciasRepository ocorrenciasRepo;
    @Autowired private UsersRepository usersRepo;


    @GetMapping public List<Ocorrencias> list(){return ocorrenciasRepo.findAll();}

    @PostMapping
    public Ocorrencias create(@RequestBody Ocorrencias ocorrencias) {
        Users user = usersRepo.findById(ocorrencias.getSeguranca().getId())
                .orElseThrow();
        ocorrencias.setSeguranca(user);
        return ocorrenciasRepo.save(ocorrencias);
    }

}
