package com.sce.platform.usuarios.service;

import com.sce.platform.empresas.dto.TenantRequest;
import com.sce.platform.usuarios.dto.UsuarioRequest;
import com.sce.platform.usuarios.dto.UsuarioResponse;
import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.entity.UsuarioTenant;
import com.sce.platform.usuarios.enums.UsuarioTenantRole;
import com.sce.platform.usuarios.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final GeneradorCodigoSce codigoSceGenerator;

    public UsuarioService(GeneradorCodigoSce codigoSceGenerator, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.codigoSceGenerator = codigoSceGenerator;
    }

    public Usuario crear(Usuario usuario, String password) {

        boolean existsByCorreo = usuarioRepository.existsByCorreo(usuario.getCorreo());
        boolean existsByNombreUsuario = usuarioRepository.existsByNombreUsuario(usuario.getNombreUsuario());

        String codigo;

        do {
            codigo = codigoSceGenerator.generar();
        } while (usuarioRepository.existsByCodigoSce(codigo));

        usuario.setCodigoSce(codigo);

        if (existsByCorreo) {
            throw new IllegalStateException("El correo ya existe");
        }

        if (existsByNombreUsuario) {
            throw new IllegalStateException("El nombre usuario ya existe");
        }

        String passwordHash = passwordEncoder.encode(password);

        usuario.setPasswordHash(passwordHash);

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public UsuarioTenant crearUsuarioEnTenant(TenantRequest tenantRequest, UsuarioRequest request, UsuarioTenantRole role) {

        // esta funcion sirve para poder crear un usuario desde el perfil owner o dentro de un tenant y asociarlo.


        UsuarioTenant usuarioTenant = new UsuarioTenant();

        return usuarioTenant;

    }


}
