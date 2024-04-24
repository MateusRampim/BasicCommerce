package com.example.demo.business.repository;

import com.example.demo.business.models.Cliente;
import com.example.demo.business.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}
