package com.group1.gestao_seguranca.controllers;

import com.group1.gestao_seguranca.entities.*;
import com.group1.gestao_seguranca.enums.TipoOcorrenciaEnum;
import com.group1.gestao_seguranca.repositories.OcorrenciasRepository;
import com.group1.gestao_seguranca.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ocorrencias")
public class OcorrenciaController {

    @Autowired
    OcorrenciasRepository ocorrenciasRepo;
    @Autowired
    UsersRepository usersRepo;

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

    @GetMapping("/tipos")
    public ResponseEntity<?> getTipos() {
        List<Map<String, String>> tipos = Arrays.stream(TipoOcorrenciaEnum.values())
                .map(tipo -> {
                    Map<String, String> item = new LinkedHashMap<>();
                    item.put("valor", tipo.name());
                    item.put("label", tipo.getLabel());
                    return item;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(tipos);
    }
}
