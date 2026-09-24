package com.sce.platform.usuarios.service;

import com.sce.platform.empresas.entity.Tenant;
import com.sce.platform.empresas.enums.TenantEstado;
import com.sce.platform.empresas.repository.TenantRepository;
import com.sce.platform.security.PasswordGenerator;
import com.sce.platform.security.SceAuthentication;
import com.sce.platform.usuarios.controller.CrearUsuarioTenantResponse;
import com.sce.platform.usuarios.dto.CrearUsuarioTenantRequest;
import com.sce.platform.usuarios.entity.*;
import com.sce.platform.usuarios.enums.UsuarioEstado;
import com.sce.platform.usuarios.enums.UsuarioTenantEstado;
import com.sce.platform.usuarios.enums.UsuarioTenantRole;
import com.sce.platform.usuarios.repository.UsuarioRepository;
import com.sce.platform.usuarios.repository.UsuarioTenantRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioTenantService {

    private final PasswordGenerator passwordGenerator;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioTenantRepository usuarioTenantRepository;
    private final UsuarioRepository usuarioRepository;
    private final TenantRepository tenantRepository;
    private final IdentifierGenerator identifierGenerator;
    private static final int MAX_TENANTS_POR_USUARIO = 2;
    private final CodigoSceGenerator codigoSceGenerator;

    public UsuarioTenantService(
         PasswordEncoder passwordEncoder,
         PasswordGenerator passwordGenerator,
         CodigoSceGenerator codigoSceGenerator,
         TenantRepository tenantRepository,
         UsuarioRepository usuarioRepository,
         UsuarioTenantRepository usuarioTenantRepository,
         IdentifierGenerator identifierGenerator
    ) {
        this.usuarioTenantRepository = usuarioTenantRepository;
        this.identifierGenerator = identifierGenerator;
        this.passwordEncoder = passwordEncoder;
        this.passwordGenerator = passwordGenerator;
        this.codigoSceGenerator = codigoSceGenerator;
        this.usuarioRepository = usuarioRepository;
        this.tenantRepository = tenantRepository;
    }
    @Transactional
    public UsuarioTenant asociar(
            Tenant tenant,
            Usuario usuario,
            UsuarioTenantRole role
    ) {

        // 1. Validar tenant
        if (tenant.getEstado() != TenantEstado.ACTIVE) {
            throw new IllegalStateException(
                    "El tenant no está activo"
            );
        }

        // 2. Validar usuario
        if (usuario.getEstado() != UsuarioEstado.ACTIVE) {
            throw new IllegalStateException(
                    "El usuario no está activo"
            );
        }

        // 3. Comprobar que no exista la relación
        boolean existe = usuarioTenantRepository
                .existsByIdTenantIdAndIdUsuarioId(
                        tenant.getId(),
                        usuario.getId()
                );

        if (existe) {
            throw new IllegalStateException(
                    "El usuario ya pertenece al tenant"
            );
        }

        long cantidad = usuarioTenantRepository.countByIdUsuarioId(usuario.getId());

        if (cantidad >= MAX_TENANTS_POR_USUARIO) {
            throw new IllegalStateException("El usuario ya pertenece al maximo de "+MAX_TENANTS_POR_USUARIO+" tenants permitidos");
        }

        // 4. Construir UsuarioTenantId
        UsuarioTenantId id = new UsuarioTenantId(
                tenant.getId(),
                usuario.getId()
        );


        String identificador = identifierGenerator.generar(usuario.getNombreUsuario(),tenant.getSlug());

        boolean identificadorExiste = usuarioTenantRepository.existsByIdentificadorSce(identificador);

        if (identificadorExiste){
            throw new IllegalStateException("El usuario ya existe en el sistema");
        }

        // 5. Crear UsuarioTenant
        UsuarioTenant usuarioTenant = new UsuarioTenant();

        usuarioTenant.setId(id);
        usuarioTenant.setUsuario(usuario);
        usuarioTenant.setTenant(tenant);
        usuarioTenant.setIdentificadorSce(identificador);
        usuarioTenant.setRole(role);
        usuarioTenant.setEstado(UsuarioTenantEstado.ACTIVE);

        // 6. Guardar
        return usuarioTenantRepository.save(usuarioTenant);
    }

    @Transactional
    public CrearUsuarioTenantResponse crearUsuarioTenant(
         CrearUsuarioTenantRequest request,
         SceAuthentication authentication
    ) {

        //evaluar rol solicitado
        // 1. Obtener el rol del usuario que está ejecutando la acción (ejemplo asumiendo que authentication.role() devuelve el enum o un String convertible)
        UsuarioTenantRole rolAutenticado = authentication.role();

        // 2. Obtener el rol que se desea asignar al nuevo usuario desde el request
        UsuarioTenantRole rolSolicitado = request.getRole();

        // 3. Evaluar la condición usando tu méthod del enum
        if (!rolAutenticado.puedeCrear(rolSolicitado)) {
            throw new AccessDeniedException("No tienes permisos para crear un usuario con el rol: " + rolSolicitado);
        }


        //procedimiento para crear usuario sin tenant asociado aun
    Usuario usuario = new Usuario();
    usuario.setNombreUsuario(request.getNombreUsuario());
    usuario.setCorreo(request.getCorreo());
    usuario.setNombres(request.getNombres());
    usuario.setApellidos(request.getApellidos());
        usuario.setEstado(UsuarioEstado.ACTIVE);

    boolean existsByNombreUsuario = usuarioRepository.existsByNombreUsuario(usuario.getNombreUsuario());

        if (existsByNombreUsuario) {
            throw new IllegalStateException("El nombre usuario ya existe");
        }
        String codigo;
        do {
            codigo = codigoSceGenerator.generar();
        } while (usuarioRepository.existsByCodigoSce(codigo));

        usuario.setCodigoSce(codigo);

        //Generar password inicial
        String passwordTemporal = passwordGenerator.generar();

        String passwordHash = passwordEncoder.encode(passwordTemporal);

        usuario.setPasswordHash(passwordHash);

        //se guarda el usuario
        usuarioRepository.save(usuario);

        Tenant tenant;
    tenant = tenantRepository.findById(authentication.tenantId()).orElseThrow(()-> new IllegalStateException("No se encontro el tenant"));

        UsuarioTenant usuariotenant = asociar(tenant, usuario, request.getRole());

        CrearUsuarioTenantResponse response =
             new CrearUsuarioTenantResponse();

        response.setUsuarioId(usuario.getId());
        response.setNombreUsuario(usuario.getNombreUsuario());
        response.setIdentificadorSce(
             usuariotenant.getIdentificadorSce()
        );
        response.setRole(usuariotenant.getRole());
        response.setPasswordTemporal(passwordTemporal);

        return response;

    }


}
