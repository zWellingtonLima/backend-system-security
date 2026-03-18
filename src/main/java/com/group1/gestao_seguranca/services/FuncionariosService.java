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

@Service
public class FuncionariosService {

    private final FuncionariosRepository funcionariosRepo;

    private final HttpServletRequest request;

    public FuncionariosService(FuncionariosRepository funcionariosRepo, HttpServletRequest request) {
        this.funcionariosRepo = funcionariosRepo;
        this.request = request;
    }

    // Metodo auxilar Obter usuario
    private Users getUserAutenticado() {
        return (Users) request.getAttribute("usuarioAutenticado");
    }


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
        funcionario.setCreateDate(LocalDateTime.now());
        funcionario.setCreateUser(user.getCreateUser());

        return FuncionariosResponseDTO.from(funcionariosRepo.save(funcionario));
    }

    public List<FuncionariosResponseDTO> listarTodos() {
        return funcionariosRepo.findAll()
                .stream()
                .map(FuncionariosResponseDTO::from)
                .toList();
    }

    public FuncionariosResponseDTO buscarPorId(int id) {
        return funcionariosRepo.findById(id)
                .map(FuncionariosResponseDTO::from)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado: id=" + id));
    }

    public FuncionariosResponseDTO buscarPorNumero(String numero) {
        return funcionariosRepo.findByNumeroFuncionario(numero)
                .map(FuncionariosResponseDTO::from)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado: número=" + numero));
    }

    public List<FuncionariosResponseDTO> buscarPorSetor(String setor) {
        return funcionariosRepo.findBySetorIgnoreCase(setor)
                .stream()
                .map(FuncionariosResponseDTO::from)
                .toList();
    }
}