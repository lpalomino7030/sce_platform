package com.sce.platform.usuarios.controller;

import com.sce.platform.empresas.entity.Tenant;
import com.sce.platform.security.SceAuthentication;
import com.sce.platform.usuarios.dto.CrearUsuarioTenantRequest;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioTenantController {

    private final UsuarioService usuarioService;
    private final UsuarioTenantService usuarioTenantService;

    public UsuarioTenantController(UsuarioService usuarioService, UsuarioTenantService usuarioTenantService) {
        this.usuarioService = usuarioService;
        this.usuarioTenantService = usuarioTenantService;
    }

    @PostMapping("/agregarUsuario")
    public UsuarioTenant crearUsuarioNuevo(
         @Valid @RequestBody CrearUsuarioTenantRequest request,
         SceAuthentication authentication
    ){
        // FIXME: refactorizando metodo para la creacion de un usuario dentro de un tenant activo y perfil activo
        return usuarioTenantService.crearUsuarioTenant(request, authentication);

    }

}
