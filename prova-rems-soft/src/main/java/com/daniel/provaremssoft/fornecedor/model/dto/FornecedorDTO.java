package com.daniel.provaremssoft.fornecedor.model.dto;

import com.daniel.provaremssoft.fornecedor.model.Fornecedor;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class FornecedorDTO {

    private UUID id;
    private String nome;
    private String cnpj;
    private String endereco;
    private String telefone;

    public FornecedorDTO(Fornecedor fornecedor) {
        this.id = fornecedor.getId();
        this.nome = fornecedor.getNome();
        this.cnpj = fornecedor.getCnpj();
        this.endereco = fornecedor.getEndereco();
        this.telefone = fornecedor.getTelefone();
    }

}
