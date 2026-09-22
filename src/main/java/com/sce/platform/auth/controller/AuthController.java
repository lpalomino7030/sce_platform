package com.sce.platform.auth.controller;

import com.sce.platform.auth.dto.*;
import com.sce.platform.auth.service.AuthService;
import com.sce.platform.security.SceAuthentication;
import com.sce.platform.security.SelectionTokenService;
import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UsuarioRepository usuarioRepository;
    private final SelectionTokenService selectionTokenService;

    public AuthController(
         AuthService authService,
         UsuarioRepository usuarioRepository,
         SelectionTokenService selectionTokenService
    ) {
        this.authService = authService;
        this.usuarioRepository = usuarioRepository;
        this.selectionTokenService = selectionTokenService;
    }


    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {

        return authService.login(loginRequest);
    }

    @PostMapping("/seleccionar")
    public TokenResponse seleccionar(
         @Valid @RequestBody TenantSeleccionadoRequest request
    ) {
        UUID usuarioId =
             selectionTokenService.validateAndGetUsuarioId(
                  request.getSelectionToken()
             );

        Usuario usuario = usuarioRepository
             .findById(usuarioId)
             .orElseThrow(() ->
                  new IllegalStateException("El usuario no existe")
             );

        return authService.seleccionarTenant(
             usuario,
             request.getTenantId()
        );
    }

    @GetMapping("/prueba")
    public String prueba(SceAuthentication authentication) {

        return "usuarioId=" + authentication.usuarioId()
                + ", tenantId=" + authentication.tenantId()
                + ", role=" + authentication.role();
    }
}
