package com.group1.gestao_seguranca.controllers;

import com.group1.gestao_seguranca.dto.auth.LoginRequestDTO;
import com.group1.gestao_seguranca.dto.auth.LoginResponseDTO;
import com.group1.gestao_seguranca.dto.auth.RegisterRequestDTO;
import com.group1.gestao_seguranca.entities.Sessao;
import com.group1.gestao_seguranca.entities.Users;
import com.group1.gestao_seguranca.repositories.SessaoRepository;
import com.group1.gestao_seguranca.repositories.UsersRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private final SessaoRepository sessaoRepo;
    private final UsersRepository usersRepo;

    public AuthController(SessaoRepository sessaoRepo, UsersRepository usersRepo) {
        this.sessaoRepo = sessaoRepo;
        this.usersRepo = usersRepo;
    }

    @PostMapping("/register")
    public ResponseEntity<?> userRegister(@Valid @RequestBody RegisterRequestDTO dto) {

        if (usersRepo.findByNumeroSeguranca(dto.getNumeroSeguranca()).isPresent()) {
            return ResponseEntity.badRequest().body("Número de segurança já registado.");
        }

        Users user = new Users(dto.getNomeSeguranca(), dto.getNumeroSeguranca(), dto.getPassword());
        user.setCreateDate(LocalDateTime.now());
        user.setCreateUser(user.getNomeSeguranca());
        usersRepo.save(user);

        return ResponseEntity.ok(true);
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@Valid @RequestBody LoginRequestDTO loginRequest) {

        Optional<Users> optUser = usersRepo.findByNumeroSeguranca(loginRequest.getNumeroSeguranca());

        if (optUser.isEmpty() || !optUser.get().getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).body("Número de Segurança ou palavra-passe incorretos.");
        }

        Users user = optUser.get();

        Optional<Sessao> sessaoAberta = sessaoRepo.findTopByUserAndHoraSaidaIsNullOrderByCreateDateDesc(user);
        if (sessaoAberta.isPresent()) {
            Sessao sessaoAnterior = sessaoAberta.get();
            sessaoAnterior.setHoraSaida(LocalDateTime.now());
            sessaoAnterior.setModifyDate(LocalDateTime.now());
            sessaoAnterior.setModifyUser("system");
            sessaoRepo.save(sessaoAnterior);
        }

        Sessao sessao = new Sessao(LocalDateTime.now(), user);
        sessao.setCreateDate(LocalDateTime.now());
        sessao.setCreateUser(user.getNomeSeguranca());
        sessao.setToken(UUID.randomUUID().toString());
        sessaoRepo.save(sessao);

        return ResponseEntity.ok(new LoginResponseDTO(sessao.getToken(), user.getId(), user.getNomeSeguranca()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("X-Sessao-Id") String token) {
        Optional<Sessao> optSessao = sessaoRepo.findByToken(token);

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
        sessaoRepo.save(sessao);

        return ResponseEntity.ok("Logout efetuado. Sessão encerrada.");
    }

    // Endpoint para recuperar sessao atual
    @GetMapping("/sessao")
    public ResponseEntity<?> getSessao(@RequestHeader("X-Sessao-Id") String token) {
        Optional<Sessao> optSessao = sessaoRepo.findByToken(token);

        if (optSessao.isEmpty()) {
            return ResponseEntity.status(404).body("Sessão não encontrada.");
        }

        Sessao sessao = optSessao.get();

        if (sessao.getHoraSaida() != null) {
            return ResponseEntity.status(401).body("Sessão expirada.");
        }

        Users user = sessao.getUser();
        return ResponseEntity.ok(new LoginResponseDTO(sessao.getToken(), user.getId(), user.getNomeSeguranca()));
    }
}
