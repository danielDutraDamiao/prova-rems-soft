package com.daniel.provaremssoft.comprador.controller;


import com.daniel.provaremssoft.comprador.model.Comprador;
import com.daniel.provaremssoft.comprador.model.dto.CompradorDTO;
import com.daniel.provaremssoft.comprador.model.dto.CompradorForm;
import com.daniel.provaremssoft.comprador.repository.CompradorRepository;
import com.daniel.provaremssoft.comprador.service.CompradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comprador")
public class CompradorController {
    
    @Autowired
    private CompradorService compradorService;
    
    @Autowired
    private CompradorRepository compradorRepository;

    @PostMapping(path = "inserir-comprador")
    public ResponseEntity<CompradorDTO> insereComprador(@RequestBody CompradorForm compradorForm){
        return ResponseEntity.ok(new CompradorDTO(this.compradorService.insereComprador(compradorForm)));
    }

    @GetMapping(path = "listar")
    public ResponseEntity<List<CompradorDTO>> listarTodosCompradores() {
        List<Comprador> compradores = this.compradorService.listarTodosCompradores();
        List<CompradorDTO> compradoresDTO = compradores.stream()
                .map(CompradorDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(compradoresDTO);
    }

    @GetMapping(path = "buscar/{id}")
    public ResponseEntity<CompradorDTO> buscaCompradorPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(new CompradorDTO(this.compradorService.buscaCompradorPorIdTela(id)));
    }

    @PutMapping(path = "alterar-comprador/{id}")
    public Comprador alterarComprador(@RequestBody CompradorForm compradorForm, @PathVariable UUID id) {
        Optional<Comprador> compradorExistente = this.compradorRepository.findById(id);

        if (compradorExistente.isPresent()) {
            Comprador compradorDadosAntiga = compradorExistente.get();
            compradorDadosAntiga.alterar(compradorForm.toModel());
            return this.compradorRepository.save(compradorDadosAntiga);
        } else {
            // Se a comprador não existe, crie uma nova com o ID especificado
            Comprador novaComprador = compradorForm.toModel();
            novaComprador.setId(id);
            return this.compradorRepository.save(novaComprador);
        }
    }

    @DeleteMapping(path = "remover/{id}")
    public ResponseEntity<Void> removeComprador(@PathVariable UUID id){
        this.compradorService.removeComprador(id);
        return ResponseEntity.noContent().build();
    }
}
