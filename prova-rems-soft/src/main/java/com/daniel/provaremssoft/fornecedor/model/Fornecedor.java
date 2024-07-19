package com.daniel.provaremssoft.fornecedor.model;

import com.daniel.provaremssoft.produto.model.Produto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class Fornecedor {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "telefone")
    private String telefone;


    public Fornecedor(UUID id, String nome, String cnpj, String endereco, String telefone ){
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public void alterar(Fornecedor fornecedor) {
        if (fornecedor.nome != null) {
            this.setNome(fornecedor.getNome());
        }

        if (fornecedor.cnpj != null) {
            this.setCnpj(fornecedor.getCnpj());
        }

        if (fornecedor.endereco != null){
            this.setEndereco(fornecedor.getEndereco());
        }

        if (fornecedor.telefone != null){
            this.setTelefone(fornecedor.getTelefone());
        }
    }
}
