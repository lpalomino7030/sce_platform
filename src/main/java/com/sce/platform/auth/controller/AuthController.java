package com.sce.platform.auth.controller;

import com.sce.platform.auth.dto.*;
import com.sce.platform.auth.service.AuthService;
import com.sce.platform.security.SceAuthentication;
import com.sce.platform.security.SelectionTokenService;
import com.sce.platform.usuarios.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/seleccionar")
    public TokenResponse seleccionar(
         @Valid @RequestBody TenantSeleccionadoRequest request
    ) {
        return authService.seleccionarTenant(request);
    }

    @GetMapping("/prueba")
    public String prueba(SceAuthentication authentication) {

        return "usuarioId=" + authentication.usuarioId()
                + ", tenantId=" + authentication.tenantId()
                + ", role=" + authentication.role();
    }
}
