package com.daniel.provaremssoft.comprador.model.dto;

import com.daniel.provaremssoft.comprador.model.Comprador;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class CompradorDTO {

    private UUID id;
    private String nome;
    private String cpf;
    private String endereco;
    private String telefone;

    public CompradorDTO(Comprador comprador) {
        this.id = comprador.getId();
        this.nome = comprador.getNome();
        this.cpf = comprador.getCpf();
        this.endereco = comprador.getEndereco();
        this.telefone = comprador.getTelefone();
    }

}
