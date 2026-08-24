package com.sce.platform.usuarios.controller;


import com.sce.platform.usuarios.dto.CrearUsuarioRequest;
import com.sce.platform.usuarios.dto.UsuarioResponse;
import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.entity.UsuarioEstado;
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
    public UsuarioResponse crear(@Valid @RequestBody CrearUsuarioRequest request){

        Usuario usuario = new Usuario();

        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setNombreUsuario(request.getNombreUsuario());
        usuario.setCorreo(request.getCorreo());
        usuario.setEstado(UsuarioEstado.ACTIVE);

        Usuario usuarioCreado = usuarioService.crear(usuario, request.getPassword());

        UsuarioResponse response = new UsuarioResponse();

        response.setId(usuarioCreado.getId());
        response.setNombreUsuario(usuarioCreado.getNombreUsuario());
        response.setCorreo(usuarioCreado.getCorreo());
        response.setNombres(usuarioCreado.getNombres());
        response.setApellidos(usuarioCreado.getApellidos());
        response.setEstado(usuarioCreado.getEstado());
        response.setFechaCreacion(usuarioCreado.getFechaCreacion());
        response.setFechaActualizacion(usuarioCreado.getFechaActualizacion());

        return response;

    }

}
