package com.daniel.provaremssoft.produto.model.dto;

import com.daniel.provaremssoft.fornecedor.model.Fornecedor;
import com.daniel.provaremssoft.produto.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoForm {

    private UUID id;

    private String nomeProduto;

    private Double valorProduto;

    private Integer quantidade;

    private UUID fornecedorId;


    // Método para converter ProdutoForm para Produto com o fornecedor fornecido
    public Produto toModel(Fornecedor fornecedor) {
        Produto produto = new Produto(id, nomeProduto, valorProduto, quantidade);
        produto.setFornecedor(fornecedor);
        return produto;
    }

    // Método sobrecarregado para converter ProdutoForm para Produto sem fornecedor
    public Produto toModel() {
        Produto produto = new Produto(id, nomeProduto, valorProduto, quantidade);
        return produto;
    }




}
