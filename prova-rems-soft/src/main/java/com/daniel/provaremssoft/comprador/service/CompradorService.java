package com.daniel.provaremssoft.comprador.service;

import com.daniel.provaremssoft.comprador.model.Comprador;
import com.daniel.provaremssoft.comprador.model.dto.CompradorForm;
import com.daniel.provaremssoft.comprador.repository.CompradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CompradorService {
    
    @Autowired
    private CompradorRepository compradorRepository;

    public Comprador insereComprador(CompradorForm compradorForm){
        return this.compradorRepository.save(compradorForm.toModel());
    }

    public List<Comprador> listarTodosCompradores() {
        return this.compradorRepository.findAll();
    }

    public Comprador buscaCompradorPorId(UUID id) {
        Optional<Comprador> comprador = this.compradorRepository.findById(id);
        return comprador.orElse(comprador.orElse(new Comprador()));
    }

    public Comprador buscaCompradorPorIdTela(UUID id) {
        Optional<Comprador> comprador = this.compradorRepository.findById(id);
        return comprador.orElse(comprador.orElse(new Comprador()));
    }

    public Comprador alterarComprador(CompradorForm comprador, UUID id){
        Comprador compradorDadosAntiga = this.buscaCompradorPorIdTela(id);
        compradorDadosAntiga.alterar(comprador.toModel());
        return this.compradorRepository.save(compradorDadosAntiga);
    }

    public void removeComprador(UUID id){
        Comprador compradorAtual = this.buscaCompradorPorId(id);
        this.compradorRepository.delete(compradorAtual);
    }
}
