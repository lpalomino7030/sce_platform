package com.sce.platform.auth.controller;

import com.sce.platform.auth.dto.*;
import com.sce.platform.auth.service.AuthService;
import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.enums.UsuarioTenantRole;
import com.sce.platform.usuarios.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public TokenResponse seleccionar(
         @Valid @RequestBody TenantSeleccionadoRequest request,
         @AuthenticationPrincipal Jwt jwt
    ) {

        UUID usuarioId = UUID.fromString(jwt.getSubject());

        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(()->new IllegalStateException("Usuario no encontrado"));


        return authService.seleccionarTenant(
             usuario,
             request.getTenantId()
        );
    }

    @GetMapping("/prueba")
    public String prueba(@AuthenticationPrincipal Jwt jwt) {

        UUID usuarioId = UUID.fromString(jwt.getSubject());

        UUID tenantId = UUID.fromString(
             jwt.getClaimAsString("tenantId")
        );

        UsuarioTenantRole role = UsuarioTenantRole.valueOf(
             jwt.getClaimAsString("role")
        );

        return "usuarioId=" + usuarioId
             + ", tenantId=" + tenantId
             + ", role=" + role;
    }
}
