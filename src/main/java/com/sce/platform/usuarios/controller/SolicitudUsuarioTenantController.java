package com.sce.platform.usuarios.controller;

import com.sce.platform.usuarios.dto.CrearSolicitudUsuarioTenantRequest;
import com.sce.platform.usuarios.entity.SolicitudUsuarioTenant;
import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.repository.UsuarioRepository;
import com.sce.platform.usuarios.service.SolicitudUsuarioTenantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/vinculacion")
public class SolicitudUsuarioTenantController {


    private final SolicitudUsuarioTenantService solicitudService;
    private final UsuarioRepository usuarioRepository;

    public SolicitudUsuarioTenantController(
            SolicitudUsuarioTenantService solicitudService,
            UsuarioRepository usuarioRepository
    ) {
        this.solicitudService = solicitudService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SolicitudUsuarioTenant crear(
            @Valid @RequestBody CrearSolicitudUsuarioTenantRequest request
    ) {

        Usuario solicitante = usuarioRepository
                .findById(UUID.fromString(
                        "80ce3f65-97b0-47bd-9082-d4da99e3b55c"
                ))
                .orElseThrow(() ->
                        new IllegalStateException(
                                "El usuario solicitante no existe"
                        )
                );

        return solicitudService.crear(
                request.getCodigoSce(),
                solicitante,
                request.getRolSolicitado()
        );
    }

    @PostMapping("/{solicitudId}/aprobar")
    public SolicitudUsuarioTenant aprobar(
            @PathVariable UUID solicitudId
    ) {

        Usuario luis = usuarioRepository
                .findByNombreUsuario("luis")
                .orElseThrow(() ->
                        new IllegalStateException(
                                "El usuario Luis no existe"
                        )
                );

        return solicitudService.aprobar(
                solicitudId,
                luis
        );
    }

}
