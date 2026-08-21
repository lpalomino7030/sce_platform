package com.sce.platform.usuarios.service;

import com.sce.platform.empresas.entity.Tenant;
import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.entity.UsuarioTenant;
import com.sce.platform.usuarios.entity.UsuarioTenantRole;
import com.sce.platform.usuarios.repository.UsuarioTenantRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioTenantService {

    private final UsuarioTenantRepository usuarioTenantRepository;
    public UsuarioTenantService(UsuarioTenantRepository usuarioTenantRepository) {
        this.usuarioTenantRepository = usuarioTenantRepository;
    }

    public UsuarioTenant asociar (Tenant tenant, Usuario usuario, UsuarioTenantRole role) {

        UsuarioTenant usuarioTenant = new UsuarioTenant();



        return
    }

}
