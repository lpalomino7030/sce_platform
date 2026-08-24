package com.sce.platform.usuarios.service;

import com.sce.platform.empresas.entity.Tenant;
import com.sce.platform.empresas.entity.TenantEstado;
import com.sce.platform.usuarios.entity.*;
import com.sce.platform.usuarios.repository.UsuarioTenantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioTenantService {

    private final UsuarioTenantRepository usuarioTenantRepository;
    public UsuarioTenantService(UsuarioTenantRepository usuarioTenantRepository) {
        this.usuarioTenantRepository = usuarioTenantRepository;
    }

    @Transactional
    public UsuarioTenant asociar(
            Tenant tenant,
            Usuario usuario,
            UsuarioTenantRole role
    ) {

        // 1. Validar tenant
        if (tenant.getEstado() != TenantEstado.ACTIVE) {
            throw new IllegalStateException(
                    "El tenant no está activo"
            );
        }

        // 2. Validar usuario
        if (usuario.getEstado() != UsuarioEstado.ACTIVE) {
            throw new IllegalStateException(
                    "El usuario no está activo"
            );
        }

        // 3. Comprobar que no exista la relación
        boolean existe = usuarioTenantRepository
                .existsByIdTenantIdAndIdUsuarioId(
                        tenant.getId(),
                        usuario.getId()
                );

        if (existe) {
            throw new IllegalStateException(
                    "El usuario ya pertenece al tenant"
            );
        }

        // 4. Construir UsuarioTenantId
        UsuarioTenantId id = new UsuarioTenantId(
                tenant.getId(),
                usuario.getId()
        );

        // 5. Crear UsuarioTenant
        UsuarioTenant usuarioTenant = new UsuarioTenant();

        usuarioTenant.setId(id);
        usuarioTenant.setUsuario(usuario);
        usuarioTenant.setTenant(tenant);
        usuarioTenant.setRole(role);
        usuarioTenant.setEstado(UsuarioTenantEstado.ACTIVE);

        // 6. Guardar
        return usuarioTenantRepository.save(usuarioTenant);
    }



}
