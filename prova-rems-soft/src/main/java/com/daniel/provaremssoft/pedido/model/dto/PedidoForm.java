package com.daniel.provaremssoft.pedido.model.dto;

import com.daniel.provaremssoft.comprador.model.Comprador;
import com.daniel.provaremssoft.pedido.model.Pedido;
import com.daniel.provaremssoft.produto.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoForm {

    private String numeroPedido;
    private Set<UUID> produtoIds;
    private UUID compradorId;

    public Pedido toModel(Set<Produto> produtos, Comprador comprador) {
        Pedido pedido = new Pedido();
        pedido.setNumeroPedido(this.numeroPedido);
        pedido.setProdutos(produtos);
        pedido.setComprador(comprador);
        // O valorTotalPedido será calculado automaticamente
        return pedido;
    }
}
