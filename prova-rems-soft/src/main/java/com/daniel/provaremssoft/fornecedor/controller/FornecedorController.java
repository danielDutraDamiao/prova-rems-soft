package com.daniel.provaremssoft.fornecedor.controller;

import com.daniel.provaremssoft.fornecedor.model.Fornecedor;
import com.daniel.provaremssoft.fornecedor.model.dto.FornecedorDTO;
import com.daniel.provaremssoft.fornecedor.model.dto.FornecedorForm;
import com.daniel.provaremssoft.fornecedor.repository.FornecedorRepository;
import com.daniel.provaremssoft.fornecedor.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/fornecedor")
public class FornecedorController {
    
    @Autowired
    FornecedorRepository fornecedorRepository;
    
    @Autowired
    FornecedorService fornecedorService;

    @PostMapping(path = "inserir-fornecedor")
    public ResponseEntity<FornecedorDTO> insereFornecedor(@RequestBody FornecedorForm fornecedorForm){
        return ResponseEntity.ok(new FornecedorDTO(this.fornecedorService.insereFornecedor(fornecedorForm)));
    }

    @GetMapping(path = "listar")
    public ResponseEntity<List<FornecedorDTO>> listarTodosFornecedores() {
        List<Fornecedor> fornecedores = this.fornecedorService.listarTodosFornecedores();
        List<FornecedorDTO> fornecedoresDTO = fornecedores.stream()
                .map(FornecedorDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(fornecedoresDTO);
    }

    @GetMapping(path = "buscar/{id}")
    public ResponseEntity<FornecedorDTO> buscaFornecedorPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(new FornecedorDTO(this.fornecedorService.buscaFornecedorPorIdTela(id)));
    }

    @PutMapping(path = "alterar-fornecedor/{id}")
    public Fornecedor alterarFornecedor(@RequestBody FornecedorForm fornecedorForm, @PathVariable UUID id) {
        Optional<Fornecedor> fornecedorExistente = this.fornecedorRepository.findById(id);

        if (fornecedorExistente.isPresent()) {
            Fornecedor fornecedorDadosAntiga = fornecedorExistente.get();
            fornecedorDadosAntiga.alterar(fornecedorForm.toModel());
            return this.fornecedorRepository.save(fornecedorDadosAntiga);
        } else {
            // Se a fornecedor não existe, crie uma nova com o ID especificado
            Fornecedor novaFornecedor = fornecedorForm.toModel();
            novaFornecedor.setId(id);
            return this.fornecedorRepository.save(novaFornecedor);
        }
    }

    @DeleteMapping(path = "remover/{id}")
    public ResponseEntity<Void> removerFornecedor(@PathVariable UUID id){
        this.fornecedorService.removerFornecedor(id);
        return ResponseEntity.noContent().build();
    }
}
