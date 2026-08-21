package com.sce.platform.usuarios.repository;

import com.sce.platform.usuarios.entity.UsuarioTenant;
import com.sce.platform.usuarios.entity.UsuarioTenantId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioTenantRepository extends JpaRepository<UsuarioTenant, UsuarioTenantId> {
}
