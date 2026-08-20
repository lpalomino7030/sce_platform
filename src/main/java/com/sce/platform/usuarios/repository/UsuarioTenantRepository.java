package com.sce.platform.usuarios.repository;

import com.sce.platform.usuarios.entity.UsuarioTenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioTenantRepository extends JpaRepository<UsuarioTenant, UUID> {
}
