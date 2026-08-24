package com.sce.platform.usuarios.service;

import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public Usuario crear(Usuario usuario, String password) {

        boolean existsByCorreo = usuarioRepository.existsByCorreo(usuario.getCorreo());
        boolean existsByNombreUsuario = usuarioRepository.existsByNombreUsuario(usuario.getNombreUsuario());


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



}
