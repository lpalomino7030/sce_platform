package com.sce.platform.usuarios.service;

import com.sce.platform.empresas.entity.Tenant;
import com.sce.platform.empresas.enums.TenantEstado;
import com.sce.platform.usuarios.entity.*;
import com.sce.platform.usuarios.enums.UsuarioEstado;
import com.sce.platform.usuarios.enums.UsuarioTenantEstado;
import com.sce.platform.usuarios.enums.UsuarioTenantRole;
import com.sce.platform.usuarios.repository.UsuarioRepository;
import com.sce.platform.usuarios.repository.UsuarioTenantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sce.platform.usuarios.enums.SolicitudUsuarioTenantEstado.PENDING;

@Service
public class UsuarioTenantService {

    private final UsuarioTenantRepository usuarioTenantRepository;
    private final UsuarioRepository usuarioRepository;
    private final GenerateIdentificador generateIdentificador;
    private static final int MAX_TENANTS_POR_USUARIO = 2;

    public UsuarioTenantService(UsuarioRepository usuarioRepository,  UsuarioTenantRepository usuarioTenantRepository, GenerateIdentificador generateIdentificador) {
        this.usuarioTenantRepository = usuarioTenantRepository;
        this.generateIdentificador = generateIdentificador;
        this.usuarioRepository = usuarioRepository;
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


        String identificador = generateIdentificador.generar(usuario.getNombreUsuario(),tenant.getSlug());

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





}
