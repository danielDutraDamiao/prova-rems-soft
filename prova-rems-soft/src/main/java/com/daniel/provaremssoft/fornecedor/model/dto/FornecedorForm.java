package com.daniel.provaremssoft.fornecedor.model.dto;

import com.daniel.provaremssoft.fornecedor.model.Fornecedor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FornecedorForm {

    private UUID id;
    private String nome;
    private String cnpj;
    private String endereco;
    private String telefone;

    public Fornecedor toModel() {return new Fornecedor(id, nome, cnpj, endereco, telefone);}
}
