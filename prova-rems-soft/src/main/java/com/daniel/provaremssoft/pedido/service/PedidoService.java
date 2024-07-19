package com.daniel.provaremssoft.pedido.service;

import com.daniel.provaremssoft.comprador.model.Comprador;
import com.daniel.provaremssoft.comprador.repository.CompradorRepository;
import com.daniel.provaremssoft.pedido.model.Pedido;
import com.daniel.provaremssoft.pedido.model.dto.PedidoForm;
import com.daniel.provaremssoft.pedido.repository.PedidoRepository;
import com.daniel.provaremssoft.produto.model.Produto;
import com.daniel.provaremssoft.produto.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CompradorRepository compradorRepository;

    public Pedido inserePedido(PedidoForm pedidoForm) {
        Set<Produto> produtos = pedidoForm.getProdutoIds().stream()
                .map(id -> produtoRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado para o ID: " + id)))
                .collect(Collectors.toSet());

        Comprador comprador = compradorRepository.findById(pedidoForm.getCompradorId())
                .orElseThrow(() -> new IllegalArgumentException("Comprador não encontrado para o ID: " + pedidoForm.getCompradorId()));

        return this.pedidoRepository.save(pedidoForm.toModel(produtos, comprador));
    }

    public Optional<Pedido> buscaPedidoPorId(UUID id) {
        return pedidoRepository.findById(id);
    }

    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarTodosPedidos() {
        return this.pedidoRepository.findAll();
    }

    public Pedido buscaPedidoPorIdTela(UUID id) {
        Optional<Pedido> pedido = this.pedidoRepository.findById(id);
        return pedido.orElse(new Pedido());
    }

    public void removePedido(UUID id){
        Pedido pedidoAtual = this.buscaPedidoPorIdTela(id);
        this.pedidoRepository.delete(pedidoAtual);
    }
}
