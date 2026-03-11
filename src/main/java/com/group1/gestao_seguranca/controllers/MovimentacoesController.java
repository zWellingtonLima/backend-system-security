package com.group1.gestao_seguranca.controllers;

import com.group1.gestao_seguranca.entities.Movimentacoes;
import com.group1.gestao_seguranca.repositories.FuncionariosRepository;
import com.group1.gestao_seguranca.repositories.MovimentacoesRepository;
import com.group1.gestao_seguranca.repositories.VisitasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movimentacoes")
public class MovimentacoesController {

    @Autowired
    FuncionariosRepository funcsRepo;
    @Autowired
    VisitasRepository visitasRepo;
    @Autowired
    MovimentacoesRepository movimRepo;

    @PostMapping
    public String createEntrada(@RequestBody Movimentacoes entrada){
        movimRepo.save(entrada);

        return "a";
    }
}
