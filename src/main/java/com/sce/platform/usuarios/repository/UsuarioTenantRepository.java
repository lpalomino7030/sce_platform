package com.sce.platform.usuarios.repository;

import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.entity.UsuarioTenant;
import com.sce.platform.usuarios.entity.UsuarioTenantId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface UsuarioTenantRepository extends JpaRepository<UsuarioTenant, UsuarioTenantId> {

    boolean existsByIdTenantIdAndIdUsuarioId (UUID tenantId, UUID usuarioId);
    List<UsuarioTenant> findByUsuario(Usuario usuario);
}
