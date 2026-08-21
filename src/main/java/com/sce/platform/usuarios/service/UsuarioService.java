package com.sce.platform.usuarios.service;

import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public Usuario crear(Usuario usuario, String password) {
        String passwordHash = passwordEncoder.encode(password);

        usuario.setPasswordHash(passwordHash);

        return usuarioRepository.save(usuario);
    }


}
