package com.sce.platform.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioPlataformaRepository extends JpaRepository<UsuarioPlataformaRepository, UUID> {

    Optional<UsuarioPlataformaRepository> findByUsuarioId(UUID usuarioId);

}