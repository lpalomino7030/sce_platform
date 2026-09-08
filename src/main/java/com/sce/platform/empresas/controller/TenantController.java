package com.sce.platform.empresas.controller;

import com.sce.platform.empresas.dto.TenantRequest;
import com.sce.platform.empresas.dto.TenantResponse;
import com.sce.platform.empresas.entity.Tenant;
import com.sce.platform.empresas.service.TenantService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping
    public TenantResponse create(@RequestBody TenantRequest request) {

        Tenant result = tenantService.crear(request);

        TenantResponse response = new TenantResponse();
        response.setNombre(result.getNombre());
        response.setRazonSocial(result.getRazonSocial());
        response.setRuc(result.getRuc());
        response.setSlug(result.getSlug());

        return response;
    }




}
