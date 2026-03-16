package com.group1.gestao_seguranca.services;

import com.group1.gestao_seguranca.dto.funcionarios.FuncionariosRequestDTO;
import com.group1.gestao_seguranca.dto.funcionarios.FuncionariosResponseDTO;
import com.group1.gestao_seguranca.entities.Funcionarios;
import com.group1.gestao_seguranca.repositories.FuncionariosRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FuncionariosService {

    private final FuncionariosRepository funcionariosRepo;

    public FuncionariosService(FuncionariosRepository funcionariosRepo) {
        this.funcionariosRepo = funcionariosRepo;
    }

    @Transactional
    public FuncionariosResponseDTO criar(FuncionariosRequestDTO dto) {
        if (funcionariosRepo.existsByNumeroFuncionario(dto.getNumeroFuncionario())) {
            throw new IllegalStateException(
                    "Já existe um funcionário com o número: " + dto.getNumeroFuncionario());
        }

        Funcionarios funcionario = new Funcionarios();
        funcionario.setNomeFuncionario(dto.getNomeFuncionario());
        funcionario.setNumeroFuncionario(dto.getNumeroFuncionario());
        funcionario.setSetor(dto.getSetor());

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