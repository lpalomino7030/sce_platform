package com.sce.platform.usuarios.service;

import com.sce.platform.empresas.entity.Tenant;
import com.sce.platform.usuarios.entity.SolicitudUsuarioTenant;
import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.entity.UsuarioTenant;
import com.sce.platform.usuarios.enums.SolicitudUsuarioTenantEstado;
import com.sce.platform.usuarios.enums.UsuarioEstado;
import com.sce.platform.usuarios.enums.UsuarioTenantEstado;
import com.sce.platform.usuarios.enums.UsuarioTenantRole;
import com.sce.platform.usuarios.repository.SolicitudUsuarioTenantRepository;
import com.sce.platform.usuarios.repository.UsuarioRepository;
import com.sce.platform.usuarios.repository.UsuarioTenantRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class SolicitudUsuarioTenantService {

    private static final int MAX_TENANTS_POR_USUARIO = 2;

    private final SolicitudUsuarioTenantRepository solicitudRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioTenantRepository usuarioTenantRepository;
    private final UsuarioTenantService usuarioTenantService;

    public SolicitudUsuarioTenantService(
            SolicitudUsuarioTenantRepository solicitudRepository,
            UsuarioRepository usuarioRepository,
            UsuarioTenantRepository usuarioTenantRepository,
            UsuarioTenantService usuarioTenantService
    ) {
        this.solicitudRepository = solicitudRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioTenantRepository = usuarioTenantRepository;
        this.usuarioTenantService = usuarioTenantService;
    }

    @Transactional
    public SolicitudUsuarioTenant crear(
            String codigoSce,
            Usuario solicitante,
            UsuarioTenantRole rolSolicitado
    ) {

        Usuario usuario = usuarioRepository.findByCodigoSce(codigoSce)
                .orElseThrow(() ->
                        new IllegalStateException("El usuario no existe")
                );

        if (usuario.getEstado() != UsuarioEstado.ACTIVE) {
            throw new IllegalStateException(
                    "El usuario no está activo"
            );
        }

        UsuarioTenant membershipSolicitante =
                usuarioTenantRepository
                        .findByUsuario(solicitante)
                        .stream()
                        .filter(ut ->
                                ut.getEstado() == UsuarioTenantEstado.ACTIVE
                        )
                        .findFirst()
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "El usuario solicitante no pertenece a ningún tenant activo"
                                )
                        );

        Tenant tenantSolicitante =
                membershipSolicitante.getTenant();

        if (usuarioTenantRepository
                .existsByIdTenantIdAndIdUsuarioId(
                        tenantSolicitante.getId(),
                        usuario.getId()
                )) {

            throw new IllegalStateException(
                    "El usuario ya pertenece al tenant"
            );
        }

        long cantidadTenants =
                usuarioTenantRepository.countByIdUsuarioId(
                        usuario.getId()
                );

        if (cantidadTenants >= MAX_TENANTS_POR_USUARIO) {
            throw new IllegalStateException(
                    "El usuario ya pertenece al máximo de tenants permitidos"
            );
        }

        boolean solicitudPendiente =
                solicitudRepository
                        .existsByUsuarioIdAndTenantSolicitanteIdAndEstado(
                                usuario.getId(),
                                tenantSolicitante.getId(),
                                SolicitudUsuarioTenantEstado.PENDING
                        );

        if (solicitudPendiente) {
            throw new IllegalStateException(
                    "Ya existe una solicitud pendiente"
            );
        }

        Tenant tenantAutorizador =
                usuarioTenantRepository
                        .findByUsuario(usuario)
                        .stream()
                        .filter(ut ->
                                ut.getEstado() == UsuarioTenantEstado.ACTIVE
                        )
                        .map(UsuarioTenant::getTenant)
                        .findFirst()
                        .orElse(null);

        SolicitudUsuarioTenant solicitud =
                new SolicitudUsuarioTenant();

        solicitud.setUsuario(usuario);
        solicitud.setTenantSolicitante(tenantSolicitante);
        solicitud.setTenantAutorizador(tenantAutorizador);
        solicitud.setRolSolicitado(rolSolicitado);
        solicitud.setEstado(
                SolicitudUsuarioTenantEstado.PENDING
        );
        solicitud.setSolicitadoPor(solicitante);
        solicitud.setFechaSolicitud(Instant.now());

        return solicitudRepository.save(solicitud);
    }


    @Transactional
    public SolicitudUsuarioTenant aprobar(
            UUID solicitudId,
            Usuario usuarioResolutor
    ) {

        SolicitudUsuarioTenant solicitud =
                solicitudRepository.findById(solicitudId)
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "La solicitud no existe"
                                )
                        );

        if (solicitud.getEstado()
                != SolicitudUsuarioTenantEstado.PENDING) {

            throw new IllegalStateException(
                    "La solicitud no está pendiente"
            );
        }

        Tenant tenantAutorizador =
                solicitud.getTenantAutorizador();

        UsuarioTenant membershipResolutor =
                usuarioTenantRepository
                        .findByUsuario(usuarioResolutor)
                        .stream()
                        .filter(ut ->
                                ut.getTenant().getId()
                                        .equals(tenantAutorizador.getId())
                        )
                        .filter(ut ->
                                ut.getEstado()
                                        == UsuarioTenantEstado.ACTIVE
                        )
                        .findFirst()
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "El usuario no pertenece al tenant autorizador"
                                )
                        );

        if (membershipResolutor.getRole()
                != UsuarioTenantRole.OWNER
                && membershipResolutor.getRole()
                != UsuarioTenantRole.ADMIN) {

            throw new IllegalStateException(
                    "El usuario no tiene permisos para resolver la solicitud"
            );
        }

        Usuario usuarioSolicitado =
                solicitud.getUsuario();

        long cantidadTenants =
                usuarioTenantRepository.countByIdUsuarioId(
                        usuarioSolicitado.getId()
                );

        if (cantidadTenants >= MAX_TENANTS_POR_USUARIO) {

            throw new IllegalStateException(
                    "El usuario ya pertenece al máximo de tenants permitidos"
            );
        }

        if (usuarioTenantRepository
                .existsByIdTenantIdAndIdUsuarioId(
                        solicitud.getTenantSolicitante().getId(),
                        usuarioSolicitado.getId()
                )) {

            throw new IllegalStateException(
                    "El usuario ya pertenece al tenant solicitante"
            );
        }

        usuarioTenantService.asociar(
                solicitud.getTenantSolicitante(),
                usuarioSolicitado,
                solicitud.getRolSolicitado()
        );

        solicitud.setEstado(
                SolicitudUsuarioTenantEstado.APPROVED
        );

        solicitud.setResueltoPor(usuarioResolutor);

        solicitud.setFechaResolucion(Instant.now());

        return solicitudRepository.save(solicitud);
    }
}
