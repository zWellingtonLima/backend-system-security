package com.group1.gestao_seguranca.service;

import com.group1.gestao_seguranca.dto.funcionarios.FuncionariosRequestDTO;
import com.group1.gestao_seguranca.dto.funcionarios.FuncionariosResponseDTO;
import com.group1.gestao_seguranca.entity.Funcionarios;
import com.group1.gestao_seguranca.entity.User;
import com.group1.gestao_seguranca.repositories.FuncionariosRepository;
import com.group1.gestao_seguranca.security.AuthUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionariosService {
    private final AuthUtils authUtils;
    private final FuncionariosRepository funcionariosRepo;

    public FuncionariosService(AuthUtils authUtils, FuncionariosRepository funcionariosRepo) {
        this.authUtils = authUtils;
        this.funcionariosRepo = funcionariosRepo;
    }

    // ====================== CREATE ======================
    @Transactional
    public FuncionariosResponseDTO criar(FuncionariosRequestDTO dto) {
        if (funcionariosRepo.existsByNumeroFuncionario(dto.getNumeroFuncionario())) {
            throw new IllegalStateException(
                    "Já existe um funcionário com o número: " + dto.getNumeroFuncionario());
        }

        Funcionarios funcionario = new Funcionarios();
        funcionario.setNome(dto.getNomeFuncionario());
        funcionario.setNumeroFuncionario(dto.getNumeroFuncionario());
        funcionario.setSetor(dto.getSetor());
//        funcionario.setCreateUser(user.getNomeSeguranca());

        Funcionarios salvo = funcionariosRepo.save(funcionario);
        return FuncionariosResponseDTO.from(salvo);
    }

    // ====================== READ ======================
    public List<FuncionariosResponseDTO> listarTodos() {
        return funcionariosRepo.findAll().stream()
                .map(FuncionariosResponseDTO::from)
                .collect(Collectors.toList());
    }

    public FuncionariosResponseDTO buscarPorId(int id) {
        Funcionarios funcionario = funcionariosRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + id + " não encontrado"));

        return FuncionariosResponseDTO.from(funcionario);
    }

    public FuncionariosResponseDTO buscarPorNumero(String numero) {
        Funcionarios funcionario = funcionariosRepo.findByNumeroFuncionario(numero)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário com número " + numero + " não encontrado"));

        return FuncionariosResponseDTO.from(funcionario);
    }

    public List<FuncionariosResponseDTO> buscarPorSetor(String setor) {
        List<Funcionarios> lista = funcionariosRepo.findBySetor(setor);
        return lista.stream()
                .map(FuncionariosResponseDTO::from)
                .collect(Collectors.toList());
    }

    // ====================== UPDATE ======================
    @Transactional
    public FuncionariosResponseDTO atualizar(int id, FuncionariosRequestDTO dto) {
        Funcionarios funcionario = funcionariosRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + id + " não encontrado"));

        if (!funcionario.isAtivo()) {
            throw new IllegalStateException("Não é possível atualizar um funcionário excluído.");
        }

        funcionario.setNome(dto.getNomeFuncionario());
        funcionario.setNumeroFuncionario(dto.getNumeroFuncionario());
        funcionario.setSetor(dto.getSetor());
        funcionario.setModifyUser(authUtils.getCurrentUserName());

        Funcionarios atualizado = funcionariosRepo.save(funcionario);
        return FuncionariosResponseDTO.from(atualizado);
    }

    // ====================== SOFT DELETE ======================
    @Transactional
    public void softDelete(int id) {
        Funcionarios funcionario = funcionariosRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + id + " não encontrado"));

        if (!funcionario.isAtivo()) {
            throw new IllegalStateException("Funcionário já foi excluído anteriormente.");
        }

        funcionario.setAtivo(false);
        funcionario.setDataExclusao(LocalDateTime.now());
        funcionario.setModifyUser(authUtils.getCurrentUserName());
        funcionariosRepo.save(funcionario);
    }
}