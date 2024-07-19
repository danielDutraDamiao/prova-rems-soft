package com.daniel.provaremssoft.fornecedor.repository;

import com.daniel.provaremssoft.fornecedor.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FornecedorRepository extends JpaRepository <Fornecedor, UUID> {

    Optional<Fornecedor> findById(UUID id);

}
