package com.sce.platform.usuarios.controller;

import com.sce.platform.security.SceAuthentication;
import com.sce.platform.usuarios.dto.CrearUsuarioTenantRequest;
import com.sce.platform.usuarios.service.UsuarioTenantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioTenantController {

    private final UsuarioTenantService usuarioTenantService;

    public UsuarioTenantController( UsuarioTenantService usuarioTenantService) {
        this.usuarioTenantService = usuarioTenantService;
    }

    @PostMapping("/agregarUsuario")
    @ResponseStatus(HttpStatus.CREATED)
    public CrearUsuarioTenantResponse crearUsuarioNuevo(
         @Valid @RequestBody CrearUsuarioTenantRequest request,
         SceAuthentication authentication
    ){
        return usuarioTenantService.crearUsuarioTenant(request, authentication);

    }

}
