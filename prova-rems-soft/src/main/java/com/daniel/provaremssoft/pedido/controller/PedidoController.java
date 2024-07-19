package com.daniel.provaremssoft.pedido.controller;

import com.daniel.provaremssoft.comprador.model.Comprador;
import com.daniel.provaremssoft.comprador.repository.CompradorRepository;
import com.daniel.provaremssoft.pedido.model.Pedido;
import com.daniel.provaremssoft.pedido.model.dto.PedidoDTO;
import com.daniel.provaremssoft.pedido.model.dto.PedidoForm;
import com.daniel.provaremssoft.pedido.service.PedidoService;
import com.daniel.provaremssoft.produto.model.Produto;
import com.daniel.provaremssoft.produto.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    CompradorRepository compradorRepository;

    @PostMapping("/inserir-pedido")
    public Pedido inserePedido(@RequestBody PedidoForm pedidoForm) {
        return pedidoService.inserePedido(pedidoForm);
    }

    @GetMapping(path = "listar")
    public ResponseEntity<List<PedidoDTO>> listarTodosPedidos() {
        List<Pedido> pedidos = this.pedidoService.listarTodosPedidos();
        List<PedidoDTO> pedidosDTO = pedidos.stream()
                .map(PedidoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pedidosDTO);
    }

    @GetMapping(path = "buscar/{id}")
    public ResponseEntity<PedidoDTO> buscaProdutoPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(new PedidoDTO(this.pedidoService.buscaPedidoPorIdTela(id)));
    }

    @PutMapping("/alterar-pedido/{id}")
    public Pedido alterarPedido(@RequestBody PedidoForm pedidoForm, @PathVariable UUID id) {
        Optional<Pedido> pedidoExistente = pedidoService.buscaPedidoPorId(id);

        if (pedidoExistente.isPresent()) {
            Pedido pedidoDadosAntiga = pedidoExistente.get();

            // Verifica se o comprador é diferente
            if (pedidoForm.getCompradorId() != null && !pedidoDadosAntiga.getComprador().getId().equals(pedidoForm.getCompradorId())) {
                Comprador novoComprador = compradorRepository.findById(pedidoForm.getCompradorId())
                        .orElseThrow(() -> new IllegalArgumentException("Comprador não encontrado para o ID: " + pedidoForm.getCompradorId()));
                pedidoDadosAntiga.setComprador(novoComprador);
            }

            // Atualiza os produtos, se algum ID de produto for fornecido
            if (pedidoForm.getProdutoIds() != null) {
                Set<Produto> novosProdutos = pedidoForm.getProdutoIds().stream()
                        .map(produtoRepository::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toSet());
                pedidoDadosAntiga.atualizarProdutos(novosProdutos);
            }

            pedidoDadosAntiga.setNumeroPedido(pedidoForm.getNumeroPedido());
            pedidoDadosAntiga.setValorTotalPedido(pedidoForm.getValorTotalPedido());

            return pedidoService.save(pedidoDadosAntiga);
        } else {
            // Se o pedido não existe, crie um novo com o ID especificado
            Set<Produto> produtos = pedidoForm.getProdutoIds().stream()
                    .map(produtoRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());

            Comprador comprador = compradorRepository.findById(pedidoForm.getCompradorId())
                    .orElseThrow(() -> new IllegalArgumentException("Comprador não encontrado para o ID: " + pedidoForm.getCompradorId()));

            Pedido novoPedido = pedidoForm.toModel(produtos, comprador);
            novoPedido.setId(id);
            return pedidoService.save(novoPedido);
        }
    }


    @DeleteMapping(path = "remover/{id}")
    public ResponseEntity<Void> removePedido(@PathVariable UUID id){
        this.pedidoService.removePedido(id);
        return ResponseEntity.noContent().build();
    }
    
}
