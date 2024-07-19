package com.daniel.provaremssoft.fornecedor.service;

import com.daniel.provaremssoft.fornecedor.model.Fornecedor;
import com.daniel.provaremssoft.fornecedor.model.dto.FornecedorForm;
import com.daniel.provaremssoft.fornecedor.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public Fornecedor insereFornecedor(FornecedorForm fornecedorForm){
        return this.fornecedorRepository.save(fornecedorForm.toModel());
    }

    public List<Fornecedor> listarTodosFornecedores() {
        return this.fornecedorRepository.findAll();
    }

    public Fornecedor buscaFornecedorPorId(UUID id) {
        Optional<Fornecedor> fornecedor = this.fornecedorRepository.findById(id);
        return fornecedor.orElse(fornecedor.orElse(new Fornecedor()));
    }

    public Fornecedor buscaFornecedorPorIdTela(UUID id) {
        Optional<Fornecedor> fornecedor = this.fornecedorRepository.findById(id);
        return fornecedor.orElse(fornecedor.orElse(new Fornecedor()));
    }

    public Fornecedor alterarFornecedor(FornecedorForm fornecedor, UUID id){
        Fornecedor fornecedorDadosAntiga = this.buscaFornecedorPorIdTela(id);
        fornecedorDadosAntiga.alterar(fornecedor.toModel());
        return this.fornecedorRepository.save(fornecedorDadosAntiga);
    }

    public void removerFornecedor(UUID id){
        Fornecedor fornecedorAtual = this.buscaFornecedorPorId(id);
        this.fornecedorRepository.delete(fornecedorAtual);
    }
}
