package com.sce.platform.usuarios.service;

import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.repository.UsuarioRepository;
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

        if (password.length() < 6) {
            throw new IllegalStateException("la contraseña es muy corta.");
        }
        String passwordHash = passwordEncoder.encode(password);

        usuario.setPasswordHash(passwordHash);

        return usuarioRepository.save(usuario);
    }



}
