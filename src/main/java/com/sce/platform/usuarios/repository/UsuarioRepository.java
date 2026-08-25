package com.sce.platform.usuarios.repository;

import com.sce.platform.usuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    boolean existsByCorreo(String correo);

    boolean existsByNombreUsuario(String nombreUsuario);

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

}
