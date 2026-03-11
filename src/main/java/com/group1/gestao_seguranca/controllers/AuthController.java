package com.group1.gestao_seguranca.controllers;

import com.group1.gestao_seguranca.entities.Sessao;
import com.group1.gestao_seguranca.entities.Users;
import com.group1.gestao_seguranca.repositories.SessaoRepository;
import com.group1.gestao_seguranca.repositories.UsersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/auth/")
public class AuthController {

    private final UsersRepository usersRepository;
    private final SessaoRepository sessaoRepository;

    public AuthController(UsersRepository usersRepository, SessaoRepository sessaoRepository) {
        this.usersRepository = usersRepository;
        this.sessaoRepository = sessaoRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> userRegister(@RequestBody Users user) {

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

        return ResponseEntity.ok(true);
    }

    // -------------------------
    // POST /auth/logout/{idSessao}
    // -------------------------
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
