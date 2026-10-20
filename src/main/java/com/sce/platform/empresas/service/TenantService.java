package com.sce.platform.empresas.service;

import com.sce.platform.empresas.dto.OwnerInicialRequest;
import com.sce.platform.empresas.dto.TenantRequest;
import com.sce.platform.empresas.dto.TenantResponse;
import com.sce.platform.empresas.entity.Tenant;
import com.sce.platform.empresas.enums.TenantEstado;
import com.sce.platform.empresas.repository.TenantRepository;
import com.sce.platform.usuarios.dto.UsuarioRequest;
import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.entity.UsuarioTenant;
import com.sce.platform.usuarios.enums.UsuarioTenantEstado;
import com.sce.platform.usuarios.enums.UsuarioTenantRole;
import com.sce.platform.usuarios.repository.UsuarioTenantRepository;
import com.sce.platform.usuarios.service.UsuarioService;
import com.sce.platform.usuarios.service.UsuarioTenantService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.sce.platform.empresas.dto.CrearTenantResponse;
import com.sce.platform.security.PasswordGenerator;
import com.sce.platform.usuarios.entity.UsuarioTenant;

@Service
public class TenantService {
    private final UsuarioTenantRepository usuarioTenantRepository;
    private final TenantRepository tenantRepository;
    private final UsuarioTenantService usuarioTenantService;
    private final UsuarioService usuarioService;
    private final SlugGenerator slugGenerator;
    private final PasswordGenerator passwordGenerator;

    public TenantService(
         UsuarioTenantRepository usuarioTenantRepository,
         UsuarioService usuarioService,
         TenantRepository tenantRepository,
         SlugGenerator slugGenerator,
         UsuarioTenantService usuarioTenantService,
         PasswordGenerator passwordGenerator
    ) {
        this.tenantRepository = tenantRepository;
        this.slugGenerator = slugGenerator;
        this.usuarioTenantService = usuarioTenantService;
        this.usuarioService = usuarioService;
        this.usuarioTenantRepository = usuarioTenantRepository;
        this.passwordGenerator = passwordGenerator;
    }

    @Transactional
    public CrearTenantResponse crear(TenantRequest request) {

        if (request.getUsuario() == null) {
            throw new IllegalArgumentException(
                 "Los datos del propietario son obligatorios"
            );
        }

        if (tenantRepository.existsByRuc(request.getRuc())) {
            throw new IllegalStateException("El tenant ya existe");
        }

        String slug = slugGenerator.generar(request.getNombre());

        if (tenantRepository.existsBySlug(slug)) {
            throw new IllegalStateException("El slug del tenant ya existe");
        }

        // 1. Crear el tenant.
        Tenant tenant = new Tenant();
        tenant.setNombre(request.getNombre());
        tenant.setRazonSocial(request.getRazonSocial());
        tenant.setRuc(request.getRuc());
        tenant.setSlug(slug);
        tenant.setEstado(TenantEstado.ACTIVE);

        Tenant tenantGuardado = tenantRepository.save(tenant);
        OwnerInicialRequest ownerRequest = request.getUsuario();

        // 2. Generar la contraseña temporal en el backend.
        String passwordTemporal = passwordGenerator.generar();

        // 3. Preparar los datos del propietario.
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setNombreUsuario(ownerRequest.getNombreUsuario());
        usuarioRequest.setCorreo(ownerRequest.getCorreo());
        usuarioRequest.setNombres(ownerRequest.getNombres());
        usuarioRequest.setApellidos(ownerRequest.getApellidos());
        usuarioRequest.setPassword(passwordTemporal);


        // 4. Crear el usuario.
        // UsuarioService se encarga de generar el hash.
        Usuario usuarioGuardado =
             usuarioService.crear(usuarioRequest);

        // 5. Asociar el propietario con el tenant.
        UsuarioTenant asociacion =
             usuarioTenantService.asociar(
                  tenantGuardado,
                  usuarioGuardado,
                  UsuarioTenantRole.OWNER
             );

        // 6. Construir la respuesta.
        CrearTenantResponse response = new CrearTenantResponse();

        // Ajustar este mapeo a los campos reales de TenantResponse.
        TenantResponse tenantResponse = new TenantResponse();
        tenantResponse.setId(tenantGuardado.getId());
        tenantResponse.setNombre(tenantGuardado.getNombre());
        tenantResponse.setRazonSocial(tenantGuardado.getRazonSocial());
        tenantResponse.setRuc(tenantGuardado.getRuc());
        tenantResponse.setSlug(tenantGuardado.getSlug());
        tenantResponse.setEstado(tenantGuardado.getEstado());

        response.setTenant(tenantResponse);

        CrearTenantResponse.UsuarioInicialResponse owner =
             new CrearTenantResponse.UsuarioInicialResponse();

        owner.setId(usuarioGuardado.getId().toString());
        owner.setNombreUsuario(usuarioGuardado.getNombreUsuario());
        owner.setCorreo(usuarioGuardado.getCorreo());
        owner.setNombres(usuarioGuardado.getNombres());
        owner.setApellidos(usuarioGuardado.getApellidos());
        owner.setCodigoSce(usuarioGuardado.getCodigoSce());
        owner.setIdentificadorSce(asociacion.getIdentificadorSce());
        owner.setRole(asociacion.getRole().name());

        response.setUsuario(owner);
        response.setPasswordTemporal(passwordTemporal);

        return response;
    }


    @Transactional
    public Usuario crearUsuarioParaTenant(
            Usuario creador,
            Tenant tenant,
            UsuarioRequest request,
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

        UsuarioRequest usuario = new UsuarioRequest();

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

        Usuario usuarioCreado =
                usuarioService.crear(
                        usuario
                );

        usuarioTenantService.asociar(
                tenant,
                usuarioCreado,
                role
        );

        return usuarioCreado;
    }


}
