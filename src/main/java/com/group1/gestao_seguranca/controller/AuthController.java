package com.group1.gestao_seguranca.controller;

import com.group1.gestao_seguranca.dto.auth.AuthResult;
import com.group1.gestao_seguranca.dto.auth.LoginRequestDTO;
import com.group1.gestao_seguranca.dto.auth.RegisterRequestDTO;
import com.group1.gestao_seguranca.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // TODO: remover register da interface pública e permitir apenas registo através de usuários do tipo admin
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO dto) {
        authService.register(dto);
        return ResponseEntity.ok(true);
    }

    @PostMapping
    public ResponseEntity<?> refresh(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        String refreshedAccessToken = authService.refresh(refreshToken);
        return ResponseEntity.ok(Map.of("accessToken", refreshedAccessToken));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO dto, HttpServletResponse response) {
        AuthResult result = authService.login(dto);

        // ------- Camada HTTP -------- Cookie de RefreshToken
        Cookie cookie = new Cookie("refreshToken", result.refreshToken());
        cookie.setHttpOnly(true);
//        cookie.setSecure(true); // Ativar para permitir apenas HTTPS
        cookie.setPath("/api/auth/refresh");
        cookie.setMaxAge(24 * 60 * 60); // 1 dia em segundos
        response.addCookie(cookie);

        return ResponseEntity.ok(result.dto());
    }

//    @PostMapping("/logout") // TODO: posso incluir aqui uma coluna no User de token_version e incrementar ou simplesmente remover do frontend o token da memória
//    public ResponseEntity<?> logout(@RequestHeader("X-Sessao-Id") String token) {
//        Optional<Sessao> optSessao = sessaoRepo.findByToken(token);
//
//        if (optSessao.isEmpty()) {
//            return ResponseEntity.badRequest().body("Sessão não encontrada.");
//        }
//
//        Sessao sessao = optSessao.get();
//
//        if (sessao.getHoraSaida() != null) {
//            return ResponseEntity.badRequest().body("Sessão já foi encerrada.");
//        }
//
//        sessao.setHoraSaida(LocalDateTime.now());
//        sessao.setModifyDate(LocalDateTime.now());
//        sessao.setModifyUser(sessao.getUser().getNome());
//        sessaoRepo.save(sessao);
//
//        return ResponseEntity.ok("Logout efetuado. Sessão encerrada.");
//    }
}
