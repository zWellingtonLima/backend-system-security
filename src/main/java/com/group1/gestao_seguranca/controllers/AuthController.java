package com.group1.gestao_seguranca.controllers;

import com.group1.gestao_seguranca.entities.Sessao;
import com.group1.gestao_seguranca.entities.Users;
import com.group1.gestao_seguranca.repositories.SessaoRepository;
import com.group1.gestao_seguranca.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    SessaoRepository sessaoRepository;

    /* /api/auth/teste
    * Endpoint para ver e testar users criados
    * */
    @GetMapping("/teste")
    public List<Users> getUsers() {
        return usersRepository.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<?> userRegister(@RequestBody Users user) {

        // Verifica se o usuário já existe
        if (usersRepository.findByNumeroSeguranca(user.getNumeroSeguranca()).isPresent()) {
            return ResponseEntity.badRequest().body("Número de segurança já registado.");
        }

        user.setCreateDate(LocalDateTime.now());
        user.setCreateUser(user.getNomeSeguranca());
        usersRepository.save(user);

        return ResponseEntity.ok("Utilizador registado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody Users loginRequest) {

        Optional<Users> optUser = usersRepository.findByNumeroSeguranca(loginRequest.getNumeroSeguranca());

        if (optUser.isEmpty() || !optUser.get().getPassword().equals(loginRequest.getPassword())) {
            //                           401 é Unauthorized
            return ResponseEntity.status(401).body("Número de Segurança ou Password incorretos.");
        }

        Users user = optUser.get();

        // Verifica se já existe sessão aberta para este utilizador
        Optional<Sessao> sessaoAberta = sessaoRepository.findByUserAndHoraSaidaIsNull(user);
        if (sessaoAberta.isPresent()) {
            return ResponseEntity.badRequest().body("Já existe uma sessão ativa para este utilizador.");
        }

        Sessao sessao = new Sessao(LocalDateTime.now(), user);
        sessao.setCreateDate(LocalDateTime.now());
        sessao.setCreateUser(user.getNomeSeguranca());
        sessaoRepository.save(sessao);

        return ResponseEntity.ok().body(sessao.getId());
    }

    @PostMapping("/logout/{idSessao}")
    public ResponseEntity<?> logout(@PathVariable Integer idSessao) {

        Optional<Sessao> optSessao = sessaoRepository.findById(idSessao);

        if (optSessao.isEmpty()) {
            return ResponseEntity.badRequest().body("Sessão não encontrada.");
        }

        Sessao sessao = optSessao.get();

        if (sessao.getHoraSaida() != null) {
            return ResponseEntity.badRequest().body("Sessão já foi encerrada.");
        }

        sessao.setHoraSaida(LocalDateTime.now());
        sessao.setModifyDate(LocalDateTime.now());
        sessao.setModifyUser(sessao.getUser().getNomeSeguranca());
        sessaoRepository.save(sessao);

        return ResponseEntity.ok("Logout efetuado. Sessão encerrada.");
    }
}
