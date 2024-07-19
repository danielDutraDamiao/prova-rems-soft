package com.daniel.provaremssoft.produto.model.dto;


import com.daniel.provaremssoft.produto.model.Produto;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class ProdutoDTO {

    private UUID id;

    private String nomeProduto;

    private Double valorProduto;

    private Integer quantidade;

    private UUID fornecedorId;


    public ProdutoDTO(Produto produto){
        this.id = produto.getId();
        this.nomeProduto = produto.getNomeProduto();
        this.valorProduto = produto.getValorProduto();
        this.quantidade = produto.getQuantidade();
        this.fornecedorId = (produto.getFornecedor() != null) ? produto.getFornecedor().getId() : null;
    }

}
