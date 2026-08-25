package com.sce.platform.auth.service;

import com.sce.platform.auth.dto.LoginRequest;
import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.entity.UsuarioEstado;
import com.sce.platform.usuarios.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario autenticar(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByNombreUsuario(request.getUsername()).orElseThrow(() ->
                new IllegalStateException("Credenciales inválidas")
        );

        String passwordHash = usuario.getPasswordHash();

        if (usuario.getEstado() != UsuarioEstado.ACTIVE) {
            throw new IllegalStateException(
                    "El usuario no está activo"
            );
        }

        if (!passwordEncoder.matches(request.getPassword(), passwordHash))
        {throw new IllegalStateException("Credenciales inválidas");
        }

        return usuario;

    }
}
