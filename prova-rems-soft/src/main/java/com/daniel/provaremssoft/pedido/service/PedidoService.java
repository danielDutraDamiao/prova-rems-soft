package com.daniel.provaremssoft.pedido.service;

import com.daniel.provaremssoft.comprador.model.Comprador;
import com.daniel.provaremssoft.comprador.repository.CompradorRepository;
import com.daniel.provaremssoft.pedido.model.Pedido;
import com.daniel.provaremssoft.pedido.model.dto.PedidoDTO;
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

    public PedidoDTO inserePedido(PedidoForm pedidoForm) {
        // Buscar produtos baseados nos IDs fornecidos
        Set<Produto> produtos = pedidoForm.getProdutoIds().stream()
                .map(produtoRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        // Buscar comprador pelo ID fornecido
        Comprador comprador = compradorRepository.findById(pedidoForm.getCompradorId())
                .orElseThrow(() -> new IllegalArgumentException("Comprador não encontrado para o ID: " + pedidoForm.getCompradorId()));

        // Criar um novo Pedido com os dados fornecidos
        Pedido pedido = pedidoForm.toModel(produtos, comprador);

        pedido.atualizarValorTotalPedido();
        pedido.atualizaValorTotalProdutosComprados();

        // Salvar o pedido no banco de dados
        Pedido salvo = pedidoRepository.save(pedido);

        // Retornar o DTO do pedido salvo
        return new PedidoDTO(salvo);
    }



    public List<PedidoDTO> listarTodosPedidos() {
        return pedidoRepository.findAll().stream().map(PedidoDTO::new).collect(Collectors.toList());
    }

    public PedidoDTO buscaPedidoPorId(UUID id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        return new PedidoDTO(pedido);
    }

    public PedidoDTO alterarPedido(PedidoForm pedidoForm, UUID id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

        if (pedidoForm.getCompradorId() != null && !pedido.getComprador().getId().equals(pedidoForm.getCompradorId())) {
            Comprador novoComprador = compradorRepository.findById(pedidoForm.getCompradorId())
                    .orElseThrow(() -> new IllegalArgumentException("Comprador não encontrado para o ID: " + pedidoForm.getCompradorId()));
            pedido.setComprador(novoComprador);
        }

        if (pedidoForm.getProdutoIds() != null && !pedidoForm.getProdutoIds().isEmpty()) {
            Set<Produto> novosProdutos = pedidoForm.getProdutoIds().stream()
                    .map(produtoRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            pedido.atualizarProdutos(novosProdutos);
        }

        if (pedidoForm.getNumeroPedido() != null) {
            pedido.setNumeroPedido(pedidoForm.getNumeroPedido());
        }

        Pedido atualizado = pedidoRepository.save(pedido);
        return new PedidoDTO(atualizado);
    }

    public void removerPedido(UUID id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        pedidoRepository.delete(pedido);
    }
}
