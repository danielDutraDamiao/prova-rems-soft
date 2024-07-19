package com.daniel.provaremssoft.comprador.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class Comprador {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "telefone")
    private String telefone;

    public Comprador(UUID id, String nome, String cpf, String endereco, String telefone ){
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public void alterar(Comprador comprador) {
        if (comprador.nome != null) {
            this.setNome(comprador.getNome());
        }

        if (comprador.cpf != null) {
            this.setCpf(comprador.getCpf());
        }

        if (comprador.endereco != null){
            this.setEndereco(comprador.getEndereco());
        }

        if (comprador.telefone != null){
            this.setTelefone(comprador.getTelefone());
        }
    }
}
