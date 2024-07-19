package com.daniel.provaremssoft.produto.controller;

import com.daniel.provaremssoft.fornecedor.model.Fornecedor;
import com.daniel.provaremssoft.fornecedor.repository.FornecedorRepository;
import com.daniel.provaremssoft.produto.model.Produto;
import com.daniel.provaremssoft.produto.model.dto.ProdutoDTO;
import com.daniel.provaremssoft.produto.model.dto.ProdutoForm;
import com.daniel.provaremssoft.produto.repository.ProdutoRepository;
import com.daniel.provaremssoft.produto.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/produto")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    ProdutoService produtoService;

    @PostMapping(path = "inserir-produto")
    public ResponseEntity<ProdutoDTO> insereProduto(@RequestBody ProdutoForm produtoForm){
        return ResponseEntity.ok(new ProdutoDTO(this.produtoService.insereProduto(produtoForm)));
    }

    @GetMapping(path = "listar")
    public ResponseEntity<List<ProdutoDTO>> listarTodosProdutos() {
        List<Produto> produtos = this.produtoService.listarTodosProdutos();
        List<ProdutoDTO> produtosDTO = produtos.stream()
                .map(ProdutoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(produtosDTO);
    }

    @GetMapping(path = "buscar/{id}")
    public ResponseEntity<ProdutoDTO> buscaProdutoPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(new ProdutoDTO(this.produtoService.buscaProdutoPorIdTela(id)));
    }

    @PutMapping("/alterar-produto/{id}")
    public Produto alterarProduto(@RequestBody ProdutoForm produtoForm, @PathVariable UUID id) {
        // Verifica se o produto existe
        Optional<Produto> produtoExistente = produtoRepository.findById(id);
        if (produtoExistente.isPresent()) {
            Produto produtoDadosAntiga = produtoExistente.get();

            UUID fornecedorId = produtoForm.getFornecedorId();
            if (fornecedorId != null) {
                Optional<Fornecedor> fornecedorOpt = fornecedorRepository.findById(fornecedorId);
                if (fornecedorOpt.isPresent()) {
                    Fornecedor fornecedor = fornecedorOpt.get();
                    produtoDadosAntiga.setFornecedor(fornecedor);
                } else {
                    throw new IllegalArgumentException("Fornecedor não encontrado para o ID: " + fornecedorId);
                }
            }

            produtoDadosAntiga.alterar(produtoForm.toModel());
            return produtoRepository.save(produtoDadosAntiga);
        } else {
            Produto novoProduto;
            UUID fornecedorId = produtoForm.getFornecedorId();

            if (fornecedorId != null) {
                Optional<Fornecedor> fornecedorOpt = fornecedorRepository.findById(fornecedorId);
                if (fornecedorOpt.isPresent()) {
                    Fornecedor fornecedor = fornecedorOpt.get();
                    novoProduto = produtoForm.toModel(fornecedor);
                } else {
                    throw new IllegalArgumentException("Fornecedor não encontrado para o ID: " + fornecedorId);
                }
            } else {
                novoProduto = produtoForm.toModel(null); // Permite fornecedor null
            }

            novoProduto.setId(id);
            return produtoRepository.save(novoProduto);
        }
    }



    @DeleteMapping(path = "remover/{id}")
    public ResponseEntity<Void> removerProduto(@PathVariable UUID id){
        this.produtoService.removerProduto(id);
        return ResponseEntity.noContent().build();
    }
}
