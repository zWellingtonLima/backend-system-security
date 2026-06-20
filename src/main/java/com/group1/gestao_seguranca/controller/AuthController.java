package com.group1.gestao_seguranca.controller;

import com.group1.gestao_seguranca.dto.auth.AuthResult;
import com.group1.gestao_seguranca.dto.auth.LoginRequestDTO;
import com.group1.gestao_seguranca.dto.auth.RegisterRequestDTO;
import com.group1.gestao_seguranca.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
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

    @GetMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue(name = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {
        if (refreshToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        String refreshedAccessToken = authService.refresh(refreshToken);

        // ------- Camada HTTP -------- Cookie de RefreshToken
        response.addHeader(HttpHeaders.SET_COOKIE, buildRefreshCookie(refreshedAccessToken).toString());
        return ResponseEntity.ok(Map.of("accessToken", refreshedAccessToken));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO dto, HttpServletResponse response) {
        AuthResult result = authService.login(dto);

        // ------- Camada HTTP -------- Cookie de RefreshToken
        response.addHeader(HttpHeaders.SET_COOKIE, buildRefreshCookie(result.refreshToken()).toString());
        return ResponseEntity.ok(result.dto());
    }

    // TODO: se for preciso do cookie em outro local, criar um arquivo CookieUtils, por exemplo.
    private ResponseCookie buildRefreshCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(false)
                .secure(false) // TODO: Ativar em produção para permitir apenas HTTPS
                .sameSite("Lax")
                .path("/") // Alterar depois para api/auth/refresh
                .maxAge(Duration.ofHours(8))
                .build();
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
