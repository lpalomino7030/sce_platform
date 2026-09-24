package com.sce.platform.usuarios.controller;


import com.sce.platform.usuarios.dto.CambiarPasswordRequest;
import com.sce.platform.security.SceAuthentication;
import com.sce.platform.usuarios.dto.UsuarioRequest;
import com.sce.platform.usuarios.dto.UsuarioResponse;
import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse crear(
         @Valid @RequestBody UsuarioRequest request
    ) {
        Usuario usuarioCreado = usuarioService.crear(request);

        UsuarioResponse response = new UsuarioResponse();

        response.setId(usuarioCreado.getId());
        response.setNombreUsuario(usuarioCreado.getNombreUsuario());
        response.setCorreo(usuarioCreado.getCorreo());
        response.setNombres(usuarioCreado.getNombres());
        response.setApellidos(usuarioCreado.getApellidos());
        response.setCodigoSce(usuarioCreado.getCodigoSce());
        response.setEstado(usuarioCreado.getEstado());
        response.setFechaCreacion(usuarioCreado.getFechaCreacion());
        response.setFechaActualizacion(usuarioCreado.getFechaActualizacion());

        return response;
    }

    @PutMapping("/me/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cambiarPassword(
         @Valid @RequestBody CambiarPasswordRequest request,
         SceAuthentication authentication
    ) {

        usuarioService.cambiarPassword(
             authentication.usuarioId(),
             request
        );
    }

}
