package com.group1.gestao_seguranca.service;

import com.group1.gestao_seguranca.dto.auth.AuthResult;
import com.group1.gestao_seguranca.dto.auth.LoginRequestDTO;
import com.group1.gestao_seguranca.dto.auth.LoginResponseDTO;
import com.group1.gestao_seguranca.dto.auth.RegisterRequestDTO;
import com.group1.gestao_seguranca.entity.User;
import com.group1.gestao_seguranca.exception.UnauthorizedException;
import com.group1.gestao_seguranca.repositories.UsersRepository;
import com.group1.gestao_seguranca.security.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsersRepository usersRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsersRepository usersRepo, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usersRepo = usersRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public void register(RegisterRequestDTO dto) {
        if (usersRepo.findByNumeroIdentificacao(dto.getNumeroIdentificacao()).isPresent()) {
            throw new IllegalStateException("Número de segurança já registado.");
        }

        User user = new User(dto.getNome(), dto.getNumeroIdentificacao(), dto.getPassword());
        user.setCreateUser(user.getNome()); // TODO: alterar quanto tiver SUPERVISOR ou ADMIN
        usersRepo.save(user);
    }

    public AuthResult login(LoginRequestDTO dto) {
        User user = usersRepo.findByNumeroIdentificacao(dto.getNumeroIdentificacao())
                .orElseThrow(() -> new UnauthorizedException("Credenciais Inválidas"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Credenciais Inválidas");
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResult(
                new LoginResponseDTO(accessToken, user.getId(), user.getNome()),
                refreshToken);
    }

    public String refresh(String refreshToken) {
        if (!jwtService.isTokenValid(refreshToken)) {
            throw new UnauthorizedException("Token inválido");
        }

        Integer userId = jwtService.extractUserId(refreshToken);
        User user = usersRepo.findById(userId)
                // TODO: alterar o tipo da Exception
                .orElseThrow(() -> new IllegalStateException("Usuário não existente"));

        return jwtService.generateAccessToken(user);
    }
}
