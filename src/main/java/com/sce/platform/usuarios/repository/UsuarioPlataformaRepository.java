package com.sce.platform.usuarios.repository;

import com.sce.platform.usuarios.entity.UsuarioPlataforma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioPlataformaRepository extends JpaRepository<UsuarioPlataforma, UUID> {

    Optional<UsuarioPlataforma> findByUsuarioId(UUID usuarioId);

}