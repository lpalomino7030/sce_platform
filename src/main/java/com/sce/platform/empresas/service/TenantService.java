package com.sce.platform.empresas.service;

import com.sce.platform.empresas.dto.TenantRequest;
import com.sce.platform.empresas.entity.Tenant;
import com.sce.platform.empresas.enums.TenantEstado;
import com.sce.platform.empresas.repository.TenantRepository;
import com.sce.platform.usuarios.dto.CrearUsuarioRequest;
import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.entity.UsuarioTenant;
import com.sce.platform.usuarios.enums.UsuarioEstado;
import com.sce.platform.usuarios.enums.UsuarioTenantEstado;
import com.sce.platform.usuarios.enums.UsuarioTenantRole;
import com.sce.platform.usuarios.repository.UsuarioTenantRepository;
import com.sce.platform.usuarios.service.UsuarioService;
import com.sce.platform.usuarios.service.UsuarioTenantService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TenantService {
    public static UsuarioTenantRepository usuarioTenantRepository;
    private final TenantRepository tenantRepository;
    private final UsuarioTenantService usuarioTenantService;
    private final UsuarioService usuarioService;
    private final SlugGenerator slugGenerator;

    public TenantService(UsuarioService usuarioService, TenantRepository tenantRepository, SlugGenerator slugGenerator, UsuarioTenantService usuarioTenantService ) {
        this.tenantRepository = tenantRepository;
        this.slugGenerator = slugGenerator;
        this.usuarioTenantService = usuarioTenantService;
        this.usuarioService = usuarioService;
    }

    @Transactional
    public Tenant crear(TenantRequest request) {

        boolean tenantExiste =
                tenantRepository.existsByRuc(request.getRuc());

        if (tenantExiste) {
            throw new RuntimeException("El tenant ya existe");
        }

        String slug = slugGenerator.generar(request.getNombre());

        if (tenantRepository.existsBySlug(slug)) {
            throw new RuntimeException("El slug del tenant ya existe");
        }

        Tenant tenant = new Tenant();

        tenant.setNombre(request.getNombre());
        tenant.setRazonSocial(request.getRazonSocial());
        tenant.setRuc(request.getRuc());
        tenant.setSlug(slug);
        tenant.setEstado(TenantEstado.ACTIVE);

        Tenant guardadoTenant = tenantRepository.save(tenant);
        //OWNER

        Usuario usuario = new Usuario();

        usuario.setNombreUsuario(request.getUsuario().getNombreUsuario());
        usuario.setCorreo(request.getUsuario().getCorreo());
        usuario.setNombres(request.getUsuario().getNombres());
        usuario.setApellidos(request.getUsuario().getApellidos());
        usuario.setEstado(UsuarioEstado.ACTIVE);

        Usuario guardadoUsuario = usuarioService.crear(
                usuario,
                request.getUsuario().getPassword()
        );

        usuarioTenantService.asociar(
                guardadoTenant,
                guardadoUsuario,
                UsuarioTenantRole.OWNER
        );

        return guardadoTenant;
    }

    @Transactional
    public Usuario crearUsuarioParaTenant(
            Usuario creador,
            Tenant tenant,
            CrearUsuarioRequest request,
            UsuarioTenantRole role
    ) {

        UsuarioTenant usuarioTenantCreador =
                usuarioTenantRepository
                        .findByIdUsuarioIdAndIdTenantId(
                                creador.getId(),
                                tenant.getId()
                        )
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "El usuario no pertenece al tenant"
                                )
                        );

        if (usuarioTenantCreador.getEstado()
                != UsuarioTenantEstado.ACTIVE) {

            throw new IllegalStateException(
                    "El usuario no tiene una membresía activa"
            );
        }

        UsuarioTenantRole roleCreador =
                usuarioTenantCreador.getRole();

        if (!roleCreador.puedeCrear(role)) {

            throw new IllegalStateException(
                    "El usuario no tiene permisos para crear "
                            + "un usuario con rol " + role
            );
        }

        Usuario usuario = new Usuario();

        usuario.setNombreUsuario(
                request.getNombreUsuario()
        );

        usuario.setCorreo(
                request.getCorreo()
        );

        usuario.setNombres(
                request.getNombres()
        );

        usuario.setApellidos(
                request.getApellidos()
        );

        usuario.setEstado(
                UsuarioEstado.ACTIVE
        );

        Usuario usuarioCreado =
                usuarioService.crear(
                        usuario,
                        request.getPassword()
                );

        usuarioTenantService.asociar(
                tenant,
                usuarioCreado,
                role
        );

        return usuarioCreado;
    }


}
