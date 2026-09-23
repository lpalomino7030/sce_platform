package com.sce.platform.usuarios.repository;

import com.sce.platform.usuarios.entity.SolicitudUsuarioTenant;
import com.sce.platform.usuarios.enums.SolicitudIntegracionEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IntegracionUsuarioRepository extends JpaRepository<SolicitudUsuarioTenant, UUID> {

    boolean existsByUsuarioIdAndTenantSolicitanteIdAndEstado(
            UUID usuarioId,
            UUID tenantSolicitanteId,
            SolicitudIntegracionEstado estado
    );

    List<SolicitudUsuarioTenant> findByTenantAutorizadorIdAndEstado(
            UUID tenantAutorizadorId,
            SolicitudIntegracionEstado estado
    );

}
