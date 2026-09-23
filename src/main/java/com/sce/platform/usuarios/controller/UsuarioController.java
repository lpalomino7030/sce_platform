package com.sce.platform.usuarios.controller;


import com.sce.platform.empresas.entity.Tenant;
import com.sce.platform.usuarios.dto.UsuarioRequest;
import com.sce.platform.usuarios.dto.UsuarioResponse;
import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.entity.UsuarioTenant;
import com.sce.platform.usuarios.enums.UsuarioEstado;
import com.sce.platform.usuarios.enums.UsuarioTenantRole;
import com.sce.platform.usuarios.service.UsuarioService;
import com.sce.platform.usuarios.service.UsuarioTenantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioTenantService usuarioTenantService;

    public UsuarioController(UsuarioService usuarioService, UsuarioTenantService usuarioTenantService) {
        this.usuarioService = usuarioService;
        this.usuarioTenantService = usuarioTenantService;
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

}
