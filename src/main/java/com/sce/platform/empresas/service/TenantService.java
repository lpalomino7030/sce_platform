package com.sce.platform.empresas.service;

import com.sce.platform.empresas.dto.TenantRequest;
import com.sce.platform.empresas.entity.Tenant;
import com.sce.platform.empresas.entity.TenantEstado;
import com.sce.platform.empresas.repository.TenantRepository;
import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.entity.UsuarioEstado;
import com.sce.platform.usuarios.entity.UsuarioTenantRole;
import com.sce.platform.usuarios.service.UsuarioService;
import com.sce.platform.usuarios.service.UsuarioTenantService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TenantService {
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


}
