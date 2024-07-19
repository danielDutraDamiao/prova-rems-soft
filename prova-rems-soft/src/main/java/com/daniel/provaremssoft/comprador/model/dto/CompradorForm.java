package com.daniel.provaremssoft.comprador.model.dto;


import com.daniel.provaremssoft.comprador.model.Comprador;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompradorForm {

    private UUID id;
    private String nome;
    private String cpf;
    private String endereco;
    private String telefone;

    public Comprador toModel() {return new Comprador(id, nome, cpf, endereco, telefone);}


}
