package com.daniel.provaremssoft.pedido.model.dto;

import com.daniel.provaremssoft.comprador.model.Comprador;
import com.daniel.provaremssoft.fornecedor.model.Fornecedor;
import com.daniel.provaremssoft.pedido.model.Pedido;
import com.daniel.provaremssoft.produto.model.Produto;
import lombok.Data;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class PedidoDTO {

    private UUID id;
    private String numeroPedido;
    private Double valorTotalPedido;
    private Set<Produto> produtos;
    private Comprador comprador;
    private String nomeFornecedor; // Adicionado para o nome do fornecedor
    private Integer totalProdutosComprados;

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.numeroPedido = pedido.getNumeroPedido();
        this.valorTotalPedido = pedido.getValorTotalPedido();
        this.produtos = pedido.getProdutos();
        this.comprador = pedido.getComprador();
        this.nomeFornecedor = pedido.getProdutos().stream()
                .findFirst()
                .map(Produto::getFornecedor) // Acessar o fornecedor do primeiro produto
                .map(Fornecedor::getNome)
                .orElse("Desconhecido");
        this.totalProdutosComprados = pedido.getTotalProdutosComprados();
    }
}