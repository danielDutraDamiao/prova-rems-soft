package com.daniel.provaremssoft.comprador.repository;

import com.daniel.provaremssoft.comprador.model.Comprador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompradorRepository extends JpaRepository<Comprador, UUID> {

    Optional<Comprador> findById(UUID id);
}
