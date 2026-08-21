package com.sce.platform.usuarios.service;

import com.sce.platform.empresas.entity.Tenant;
import com.sce.platform.usuarios.entity.*;
import com.sce.platform.usuarios.repository.UsuarioTenantRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioTenantService {

    private final UsuarioTenantRepository usuarioTenantRepository;
    public UsuarioTenantService(UsuarioTenantRepository usuarioTenantRepository) {
        this.usuarioTenantRepository = usuarioTenantRepository;
    }

    public UsuarioTenant asociar (Tenant tenant, Usuario usuario, UsuarioTenantRole role) {

        UsuarioTenantId id = new UsuarioTenantId(
               tenant.getId(),
                usuario.getId()
        );

        UsuarioTenant usuarioTenant = new UsuarioTenant();

        usuarioTenant.setId(id);
        usuarioTenant.setUsuario(usuario);
        usuarioTenant.setTenant(tenant);
        usuarioTenant.setRole(role);
        usuarioTenant.setEstado(UsuarioTenantEstado.ACTIVE);

        return  usuarioTenantRepository.save(usuarioTenant);
    }

}
