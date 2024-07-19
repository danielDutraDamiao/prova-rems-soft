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

    private UUID id;
    private String numeroPedido;
    private Double valorTotalPedido;
    private Set<UUID> produtoIds;
    private UUID compradorId;

    public Pedido toModel(Set<Produto> produtos, Comprador comprador) {
        return new Pedido(id, numeroPedido, valorTotalPedido, produtos, comprador);
    }

}
