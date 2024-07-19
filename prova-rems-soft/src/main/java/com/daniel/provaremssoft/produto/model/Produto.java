package com.daniel.provaremssoft.produto.model;

import com.daniel.provaremssoft.fornecedor.model.Fornecedor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "nome_produto")
    private String nomeProduto;

    @Column(name = "valor_produto")
    private Double valorProduto;

    @Column(name = "quantidade")
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "id_fornecedor")
    private Fornecedor fornecedor;

    // Construtor para uso em DTO sem fornecedor
    public Produto(UUID id, String nomeProduto, Double valorProduto, Integer quantidade) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.valorProduto = valorProduto;
        this.quantidade = quantidade;
    }

    // Construtor para uso em DTO com fornecedor
    public Produto(UUID id, String nomeProduto, Double valorProduto, Integer quantidade, Fornecedor fornecedor) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.valorProduto = valorProduto;
        this.quantidade = quantidade;
        this.fornecedor = fornecedor;
    }

    public void alterar(Produto produto) {
        if (produto.getNomeProduto() != null) {
            this.setNomeProduto(produto.getNomeProduto());
        }

        if (produto.getValorProduto() != null) {
            this.setValorProduto(produto.getValorProduto());
        }

        if (produto.getQuantidade() != null) {
            this.setQuantidade(produto.getQuantidade());
        }

        // Atualiza o fornecedor se presente
        if (produto.getFornecedor() != null) {
            this.setFornecedor(produto.getFornecedor());
        }
    }
}
