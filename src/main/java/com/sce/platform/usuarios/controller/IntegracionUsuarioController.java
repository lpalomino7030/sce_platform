package com.sce.platform.usuarios.controller;

import com.sce.platform.security.SceAuthentication;
import com.sce.platform.usuarios.dto.IntegracionUsuarioRequest;
import com.sce.platform.usuarios.entity.SolicitudUsuarioTenant;
import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.repository.UsuarioRepository;
import com.sce.platform.usuarios.service.IntegracionUsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/vinculacion")
public class IntegracionUsuarioController {


    private final IntegracionUsuarioService solicitudService;
    private final UsuarioRepository usuarioRepository;

    public IntegracionUsuarioController(
            IntegracionUsuarioService solicitudService,
            UsuarioRepository usuarioRepository
    ) {
        this.solicitudService = solicitudService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SolicitudUsuarioTenant crear(
         @Valid @RequestBody IntegracionUsuarioRequest request
    , SceAuthentication authentication) {

        return solicitudService.crearIntegracionUsuario(request, authentication);
    }

    @PostMapping("/{solicitudId}/aprobar")
    public SolicitudUsuarioTenant aprobar(
            @PathVariable UUID solicitudId, SceAuthentication authentication
    ) {
        return solicitudService.aprobar(
                solicitudId,
                authentication
        );
    }

}
