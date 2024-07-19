package com.daniel.provaremssoft.pedido.model.dto;

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
    private Set<UUID> produtoIds;
    private UUID compradorId;

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.numeroPedido = pedido.getNumeroPedido();
        this.valorTotalPedido = pedido.getValorTotalPedido();
        this.produtoIds = pedido.getProdutos().stream().map(Produto::getId).collect(Collectors.toSet());
        this.compradorId = pedido.getComprador().getId();
    }

}
