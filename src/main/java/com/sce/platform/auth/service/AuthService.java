package com.sce.platform.auth.service;

import com.sce.platform.auth.dto.LoginRequest;
import com.sce.platform.auth.dto.LoginResponse;
import com.sce.platform.auth.dto.TenantDisponibleResponse;
import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.entity.UsuarioEstado;
import com.sce.platform.usuarios.entity.UsuarioTenant;
import com.sce.platform.usuarios.entity.UsuarioTenantEstado;
import com.sce.platform.usuarios.repository.UsuarioRepository;
import com.sce.platform.usuarios.repository.UsuarioTenantRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioTenantRepository usuarioTenantRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, UsuarioTenantRepository usuarioTenantRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioTenantRepository = usuarioTenantRepository;
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

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Usuario usuario = autenticar(request);

        List<UsuarioTenant> relaciones = usuarioTenantRepository.findByUsuario(usuario).stream().filter(relacion ->
                relacion.getEstado() == UsuarioTenantEstado.ACTIVE
        ).toList();

        //Relaciones
        List<TenantDisponibleResponse> tenants =
                relaciones.stream()
                        .map(relacion -> {
                            TenantDisponibleResponse dto =
                                    new TenantDisponibleResponse();

                            dto.setId(relacion.getTenant().getId());
                            dto.setNombre(relacion.getTenant().getNombre());
                            dto.setRole(relacion.getRole());

                            return dto;
                        })
                        .toList();

        //Login response

        LoginResponse response = new LoginResponse();
        response.setTenants(tenants);
        response.setNombres(usuario.getNombres());
        response.setNombreUsuario(usuario.getNombreUsuario());
        response.setUsuarioId(usuario.getId());


        return response;
    }




}
