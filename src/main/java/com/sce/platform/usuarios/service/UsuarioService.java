package com.sce.platform.usuarios.service;

import com.sce.platform.usuarios.dto.UsuarioRequest;
import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.enums.UsuarioEstado;
import com.sce.platform.usuarios.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final CodigoSceGenerator codigoSceGenerator;

    public UsuarioService(CodigoSceGenerator codigoSceGenerator, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.codigoSceGenerator = codigoSceGenerator;
    }

    public Usuario crear(UsuarioRequest request) {

        Usuario usuario = new Usuario();

        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setNombreUsuario(request.getNombreUsuario());
        usuario.setCorreo(request.getCorreo());
        usuario.setEstado(UsuarioEstado.ACTIVE);


        boolean existsByCorreo = usuarioRepository.existsByCorreo(usuario.getCorreo());
        boolean existsByNombreUsuario = usuarioRepository.existsByNombreUsuario(usuario.getNombreUsuario());

        String codigo;

        if (existsByCorreo) {
            throw new IllegalStateException("El correo ya existe");
        }

        if (existsByNombreUsuario) {
            throw new IllegalStateException("El nombre usuario ya existe");
        }

        do {
            codigo = codigoSceGenerator.generar();
        } while (usuarioRepository.existsByCodigoSce(codigo));

        usuario.setCodigoSce(codigo);

        String password = request.getPassword();

        String passwordHash = passwordEncoder.encode(password);

        usuario.setPasswordHash(passwordHash);

        return usuarioRepository.save(usuario);
    }



}
