package com.sce.platform.empresas.service;

import com.sce.platform.empresas.dto.TenantRequest;
import com.sce.platform.empresas.entity.Tenant;
import com.sce.platform.empresas.entity.TenantEstado;
import com.sce.platform.empresas.repository.TenantRepository;
import org.springframework.stereotype.Service;

@Service
public class TenantService {
    private final TenantRepository tenantRepository;
    private final SlugGenerator slugGenerator;

    public TenantService(TenantRepository tenantRepository, SlugGenerator slugGenerator ) {
        this.tenantRepository = tenantRepository;
        this.slugGenerator = slugGenerator;
    }

    public Tenant crear(TenantRequest request) {

        boolean tenantExiste =
                tenantRepository.existsByRuc(request.getRuc());

        if (tenantExiste) {
            throw new RuntimeException("El tenant ya existe");
        }

        String slug = slugGenerator.generar(request.getNombre());

        if (tenantRepository.existsBySlug(slug)) {
            throw new RuntimeException("El slug del tenant ya existe");
        }

        Tenant tenant = new Tenant();

        tenant.setNombre(request.getNombre());
        tenant.setRazonSocial(request.getRazonSocial());
        tenant.setRuc(request.getRuc());
        tenant.setSlug(slug);
        tenant.setEstado(TenantEstado.ACTIVE);

        return tenantRepository.save(tenant);
    }


}
