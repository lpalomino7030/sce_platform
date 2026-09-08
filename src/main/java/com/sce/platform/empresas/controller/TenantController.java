package com.sce.platform.empresas.controller;

import com.sce.platform.empresas.dto.TenantRequest;
import com.sce.platform.empresas.dto.TenantResponse;
import com.sce.platform.empresas.entity.Tenant;
import com.sce.platform.empresas.service.TenantService;
import com.sce.platform.usuarios.dto.UsuarioRequest;
import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.entity.UsuarioTenant;
import com.sce.platform.usuarios.enums.UsuarioEstado;
import com.sce.platform.usuarios.enums.UsuarioTenantRole;
import com.sce.platform.usuarios.service.UsuarioService;
import com.sce.platform.usuarios.service.UsuarioTenantService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final TenantService tenantService;
    private final UsuarioService usuarioService;
    private final UsuarioTenantService usuarioTenantService;

    public TenantController(
            TenantService tenantService, UsuarioService usuarioService, UsuarioTenantService usuarioTenantService) {
        this.tenantService = tenantService;
        this.usuarioService = usuarioService;
        this.usuarioTenantService = usuarioTenantService;
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
