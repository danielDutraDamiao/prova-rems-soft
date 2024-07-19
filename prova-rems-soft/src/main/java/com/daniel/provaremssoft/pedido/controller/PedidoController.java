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
    public ResponseEntity<PedidoDTO> inserePedido(@RequestBody PedidoForm pedidoForm) {
        PedidoDTO pedidoDTO = pedidoService.inserePedido(pedidoForm);
        return ResponseEntity.ok(pedidoDTO);
    }

    @GetMapping(path = "listar")
    public ResponseEntity<List<PedidoDTO>> listarTodosPedidos() {
        List<PedidoDTO> pedidosDTO = this.pedidoService.listarTodosPedidos();
        return ResponseEntity.ok(pedidosDTO);
    }

    @GetMapping(path = "buscar/{id}")
    public ResponseEntity<PedidoDTO> buscaPedidoPorId(@PathVariable UUID id) {
        PedidoDTO pedidoDTO = this.pedidoService.buscaPedidoPorId(id);
        return ResponseEntity.ok(pedidoDTO);
    }

    @PutMapping("/alterar-pedido/{id}")
    public ResponseEntity<PedidoDTO> alterarPedido(@RequestBody PedidoForm pedidoForm, @PathVariable UUID id) {
        PedidoDTO pedidoDTO = pedidoService.alterarPedido(pedidoForm, id);
        return ResponseEntity.ok(pedidoDTO);
    }

    @DeleteMapping(path = "remover/{id}")
    public ResponseEntity<Void> removePedido(@PathVariable UUID id) {
        this.pedidoService.removerPedido(id);
        return ResponseEntity.noContent().build();
    }


}
