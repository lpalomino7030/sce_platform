package com.sce.platform.auth.controller;

import com.sce.platform.auth.dto.*;
import com.sce.platform.auth.service.AuthService;
import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/prueba")
    public String prueba() {
        return "Acceso autorizado";
    }
}
