package com.sce.platform.empresas.service;

import com.sce.platform.empresas.entity.Tenant;
import com.sce.platform.empresas.repository.TenantRepository;
import com.sce.platform.usuarios.entity.UsuarioTenant;
import org.springframework.stereotype.Service;

@Service
public class TenantService {
    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public Tenant crear(Tenant tenant) {
        return tenantRepository.save(tenant);
    }


}
