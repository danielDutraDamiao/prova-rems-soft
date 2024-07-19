package com.daniel.provaremssoft.pedido.model;

import com.daniel.provaremssoft.comprador.model.Comprador;
import com.daniel.provaremssoft.produto.model.Produto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "numero_pedido")
    private String numeroPedido;

    @Column(name = "valor_total_pedido")
    private Double valorTotalPedido;

    private Integer totalProdutosComprados;

    @ManyToMany
    @JoinTable(
            name = "pedido_produto",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private Set<Produto> produtos;

    @ManyToOne
    @JoinColumn(name = "id_comprador")
    private Comprador comprador;

    public Pedido(UUID id, String numeroPedido, Double valorTotalPedido, Set<Produto> produtos, Comprador comprador) {
        this.id = id;
        this.numeroPedido = numeroPedido;
        this.valorTotalPedido = valorTotalPedido;
        this.produtos = produtos;
        this.comprador = comprador;
    }

    public void atualizarProdutos(Set<Produto> novosProdutos) {
        this.produtos = novosProdutos;
        atualizarValorTotalPedido();
    }

    public void alterar(Pedido pedido) {
        if (pedido.numeroPedido != null) {
            this.setNumeroPedido(pedido.getNumeroPedido());
        }
        if (pedido.comprador != null) {
            this.setComprador(pedido.getComprador());
        }
        if (pedido.produtos != null && !pedido.produtos.isEmpty()) {
            this.atualizarProdutos(pedido.getProdutos());
        }
    }

    public void atualizarValorTotalPedido() {
        this.valorTotalPedido = produtos.stream()
                .mapToDouble(Produto::getValorProduto)
                .sum();
    }

    public void atualizaValorTotalProdutosComprados() {
        this.totalProdutosComprados =  produtos.size();
    }

    public String getNomeFornecedor() {
        return produtos.stream()
                .map(Produto::getFornecedor) // Assumindo que Produto tem um método getFornecedor()
                .map(fornecedor -> fornecedor.getNome()) // Assumindo que Fornecedor tem um método getNome()
                .findFirst()
                .orElse("Fornecedor não encontrado");
    }
}
