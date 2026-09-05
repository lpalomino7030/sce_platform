package com.sce.platform.usuarios.repository;

import com.sce.platform.usuarios.entity.SolicitudUsuarioTenant;
import com.sce.platform.usuarios.enums.SolicitudUsuarioTenantEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SolicitudUsuarioTenantRepository extends JpaRepository<SolicitudUsuarioTenant, UUID> {

    boolean existsByUsuarioIdAndTenantSolicitanteIdAndEstado(
            UUID usuarioId,
            UUID tenantSolicitanteId,
            SolicitudUsuarioTenantEstado estado
    );

    List<SolicitudUsuarioTenant> findByTenantAutorizadorIdAndEstado(
            UUID tenantAutorizadorId,
            SolicitudUsuarioTenantEstado estado
    );

}
