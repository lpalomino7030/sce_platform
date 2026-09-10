package com.sce.platform.auth.controller;

import com.sce.platform.auth.dto.LoginRequest;
import com.sce.platform.auth.dto.LoginResponse;
import com.sce.platform.auth.dto.TenantSeleccionadoRequest;
import com.sce.platform.auth.dto.TenantSeleccionadoResponse;
import com.sce.platform.auth.service.AuthService;
import com.sce.platform.empresas.dto.TenantRequest;
import com.sce.platform.empresas.dto.TenantResponse;
import com.sce.platform.usuarios.dto.UsuarioRequest;
import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.entity.UsuarioTenant;
import com.sce.platform.usuarios.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UsuarioRepository usuarioRepository;

    public AuthController(
         AuthService authService,
         UsuarioRepository usuarioRepository
    ) {
        this.authService = authService;
        this.usuarioRepository = usuarioRepository;
    }


    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {

        return authService.login(loginRequest);
    }

    @PostMapping("/seleccionar")
    public TenantSeleccionadoResponse seleccionar(
         @Valid @RequestBody TenantSeleccionadoRequest request
    ) {

        Usuario usuario = usuarioRepository
             .findByNombreUsuario("luis")
             .orElseThrow(() ->
                  new IllegalStateException("El usuario no existe")
             );

        return authService.seleccionarTenant(
             usuario,
             request.getTenantId()
        );
    }
}
