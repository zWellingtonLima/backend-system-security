package com.group1.gestao_seguranca.services;

import com.group1.gestao_seguranca.dto.funcionarios.FuncionariosRequestDTO;
import com.group1.gestao_seguranca.dto.funcionarios.FuncionariosResponseDTO;
import com.group1.gestao_seguranca.entities.Funcionarios;
import com.group1.gestao_seguranca.entities.Users;
import com.group1.gestao_seguranca.repositories.FuncionariosRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionariosService {
    private final HttpServletRequest request;
    private final FuncionariosRepository funcionariosRepo;

    public FuncionariosService(HttpServletRequest request, FuncionariosRepository repository) {
        this.request = request;
        this.funcionariosRepo = repository;
    }

    // ====================== CREATE ======================
    @Transactional
    public FuncionariosResponseDTO criar(FuncionariosRequestDTO dto) {
        if (funcionariosRepo.existsByNumeroFuncionario(dto.getNumeroFuncionario())) {
            throw new IllegalStateException(
                    "Já existe um funcionário com o número: " + dto.getNumeroFuncionario());
        }

        Users user = getUserAutenticado();

        Funcionarios funcionario = new Funcionarios();
        funcionario.setNomeFuncionario(dto.getNomeFuncionario());
        funcionario.setNumeroFuncionario(dto.getNumeroFuncionario());
        funcionario.setSetor(dto.getSetor());
        funcionario.setCreateUser(user.getNomeSeguranca());

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

        Users user = getUserAutenticado();

        funcionario.setNomeFuncionario(dto.getNomeFuncionario());
        funcionario.setNumeroFuncionario(dto.getNumeroFuncionario());
        funcionario.setSetor(dto.getSetor());
        funcionario.setModifyUser(user.getNomeSeguranca());

        Funcionarios atualizado = funcionariosRepo.save(funcionario);
        return FuncionariosResponseDTO.from(atualizado);
    }

    // ====================== SOFT DELETE ======================
    @Transactional
    public void softDelete(int id) {
        Users user = getUserAutenticado();
        Funcionarios funcionario = funcionariosRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + id + " não encontrado"));

        if (!funcionario.isAtivo()) {
            throw new IllegalStateException("Funcionário já foi excluído anteriormente.");
        }

        funcionario.setAtivo(false);
        funcionario.setDataExclusao(LocalDateTime.now());
        funcionario.setModifyUser(user.getNomeSeguranca());
        funcionariosRepo.save(funcionario);
    }

    private Users getUserAutenticado() {
        return (Users) request.getAttribute("usuarioAutenticado");
    }
}