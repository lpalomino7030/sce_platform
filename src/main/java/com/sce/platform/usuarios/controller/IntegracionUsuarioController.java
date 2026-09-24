package com.sce.platform.usuarios.controller;

import com.sce.platform.security.SceAuthentication;
import com.sce.platform.usuarios.dto.IntegracionUsuarioRequest;
import com.sce.platform.usuarios.entity.SolicitudUsuarioTenant;
import com.sce.platform.usuarios.service.IntegracionUsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/vinculacion")
public class IntegracionUsuarioController {


    private final IntegracionUsuarioService solicitudService;

    public IntegracionUsuarioController(
            IntegracionUsuarioService solicitudService
    ) {
        this.solicitudService = solicitudService;
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
