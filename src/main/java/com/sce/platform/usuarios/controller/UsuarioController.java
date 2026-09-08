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
    public UsuarioResponse crear(@Valid @RequestBody UsuarioRequest request){

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
        response.setCodigoSce(usuarioCreado.getCodigoSce());
        response.setEstado(usuarioCreado.getEstado());
        response.setFechaCreacion(usuarioCreado.getFechaCreacion());
        response.setFechaActualizacion(usuarioCreado.getFechaActualizacion());

        return response;

    }


    /*
*
Esta funcion permite al tenant crear un usuario y registrarlo en su lista de usuarios/empleados.
* Requiere:
* UsuarioRequest, Tenant, UsuarioRol
* al crearse un nuevo usuario este tenga una contraseña por default y un estado ACTIVE
*
 */
    @PostMapping("/nuevo_usuario")
    public UsuarioTenant crearUsuarioNuevo(@Valid @RequestBody UsuarioRequest request, Tenant tenant, UsuarioTenantRole role){

        System.out.println("========== ENTRE A NUEVO USUARIO ==========");

        Usuario usuario = new Usuario();

        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setNombreUsuario(request.getNombreUsuario());
        usuario.setCorreo(request.getCorreo());
        usuario.setEstado(UsuarioEstado.ACTIVE);

        System.out.println("CREANDO: " + request.getNombreUsuario() + " / " + request.getCorreo());

        Usuario usuarioCreado = usuarioService.crear(
                usuario,
                "SCE2026" + request.getNombreUsuario()
        );

        UsuarioTenant usuarioAgregado = usuarioTenantService.asociar(tenant, usuarioCreado, role);

        return usuarioAgregado;
    }
}
