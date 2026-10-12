
package com.sce.platform.usuarios.service;

import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.entity.UsuarioPlataforma;
import com.sce.platform.usuarios.enums.PlatformRole;
import com.sce.platform.usuarios.enums.UsuarioEstado;
import com.sce.platform.usuarios.repository.UsuarioPlataformaRepository;
import com.sce.platform.usuarios.repository.UsuarioRepository;
import com.sce.platform.usuarios.repository.UsuarioTenantRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UsuarioPlataformaService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioPlataformaRepository plataformaRepository;
    private final UsuarioTenantRepository usuarioTenantRepository;

    public UsuarioPlataformaService(
         UsuarioRepository usuarioRepository,
         UsuarioPlataformaRepository plataformaRepository,
         UsuarioTenantRepository usuarioTenantRepository) {
        this.usuarioRepository = usuarioRepository;
        this.plataformaRepository = plataformaRepository;
        this.usuarioTenantRepository = usuarioTenantRepository;
    }

    @Transactional
    public UsuarioPlataforma asignarRol(
         UUID usuarioId,
         PlatformRole role) {

        if (role == null) {
            throw new IllegalArgumentException(
                 "El rol de plataforma es obligatorio");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
             .orElseThrow(() ->
                  new IllegalArgumentException(
                       "El usuario no existe"));

        if (usuario.getEstado() != UsuarioEstado.ACTIVE) {
            throw new IllegalStateException(
                 "El usuario debe estar activo");
        }

        if (plataformaRepository.existsByUsuario_Id(usuarioId)) {
            throw new IllegalStateException(
                 "El usuario ya tiene acceso a la plataforma");
        }

        if (usuarioTenantRepository.existsByUsuario_Id(usuarioId)) {
            throw new IllegalStateException(
                 "El usuario ya tiene una membresía empresarial");
        }

        UsuarioPlataforma plataforma = new UsuarioPlataforma();
        plataforma.setUsuario(usuario);
        plataforma.setRolePlataforma(role);

        return plataformaRepository.save(plataforma);
    }
}
