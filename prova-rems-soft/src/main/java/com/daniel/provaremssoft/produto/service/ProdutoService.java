package com.daniel.provaremssoft.produto.service;

import com.daniel.provaremssoft.fornecedor.model.Fornecedor;
import com.daniel.provaremssoft.fornecedor.repository.FornecedorRepository;
import com.daniel.provaremssoft.produto.model.Produto;
import com.daniel.provaremssoft.produto.model.dto.ProdutoForm;
import com.daniel.provaremssoft.produto.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoService {


    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public Produto insereProduto(ProdutoForm produtoForm) {
        // Buscar o fornecedor pelo ID
        Optional<Fornecedor> fornecedorOpt = fornecedorRepository.findById(produtoForm.getFornecedorId());
        if (!fornecedorOpt.isPresent()) {
            throw new IllegalArgumentException("Fornecedor não encontrado para o ID: " + produtoForm.getFornecedorId());
        }

        Fornecedor fornecedor = fornecedorOpt.get();
        Produto produto = produtoForm.toModel(fornecedor);
        return this.produtoRepository.save(produto);
    }

    public List<Produto> listarTodosProdutos() {
        return this.produtoRepository.findAll();
    }

    public Produto buscaProdutoPorId(UUID id) {
        Optional<Produto> produto = this.produtoRepository.findById(id);
        return produto.orElse(new Produto());
    }

    public Produto buscaProdutoPorIdTela(UUID id) {
        Optional<Produto> produto = this.produtoRepository.findById(id);
        return produto.orElse(new Produto());
    }

    public Produto alterarExtensaoProduto(ProdutoForm produto, UUID id){
        Produto produtoDadosAntiga = this.buscaProdutoPorIdTela(id);
        // Buscar o fornecedor pelo ID no ProdutoForm
        Optional<Fornecedor> fornecedorOpt = fornecedorRepository.findById(produto.getFornecedorId());
        if (fornecedorOpt.isPresent()) {
            Fornecedor fornecedor = fornecedorOpt.get();
            Produto produtoNovo = produto.toModel(fornecedor);
            produtoDadosAntiga.alterar(produtoNovo);
        } else {
            produtoDadosAntiga.alterar(produto.toModel(null));
        }
        return this.produtoRepository.save(produtoDadosAntiga);
    }

    public void removerProduto(UUID id){
        Produto produtoAtual = this.buscaProdutoPorId(id);
        this.produtoRepository.delete(produtoAtual);
    }

}
