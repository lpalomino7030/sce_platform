package com.sce.platform.auth.service;

import com.sce.platform.auth.dto.*;
import com.sce.platform.empresas.enums.TenantEstado;
import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.enums.UsuarioEstado;
import com.sce.platform.usuarios.entity.UsuarioTenant;
import com.sce.platform.usuarios.enums.UsuarioTenantEstado;
import com.sce.platform.usuarios.repository.UsuarioRepository;
import com.sce.platform.usuarios.repository.UsuarioTenantRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public ResultadoAutenticacion autenticar(LoginRequest request) {


        Usuario usuario = null;
        UsuarioTenant usuarioTenant = null;
        if (request.getUsername().contains("@")) {
            usuarioTenant = datosIdentificador(request.getUsername())
                    .orElseThrow(() ->
                            new IllegalStateException(
                                    "El identificador no existe en el sistema"
                            )
                    );

            usuario = usuarioTenant.getUsuario();

        } else{
            usuario = usuarioRepository.findByNombreUsuario(request.getUsername()).orElseThrow(() ->
                    new IllegalStateException("Credenciales inválidas")
            );
        }

        String passwordHash = usuario.getPasswordHash();

        if (usuario.getEstado() != UsuarioEstado.ACTIVE) {
            throw new IllegalStateException(
                    "El usuario no está activo"
            );
        }

        if (!passwordEncoder.matches(request.getPassword(), passwordHash))
        {throw new IllegalStateException("Credenciales inválidas");
        }

        ResultadoAutenticacion resultado = new ResultadoAutenticacion();
        resultado.setUsuario(usuario);
        resultado.setUsuarioTenant(usuarioTenant);

        return resultado;

    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {


        ResultadoAutenticacion autenticacion = autenticar(request);

        Usuario usuario = autenticacion.getUsuario();
        UsuarioTenant usuarioTenant = autenticacion.getUsuarioTenant();

        LoginResponse response = new LoginResponse();

        response.setNombres(usuario.getNombres());
        response.setNombreUsuario(usuario.getNombreUsuario());
        response.setUsuarioId(usuario.getId());

        if (usuarioTenant != null) {
            TenantDisponibleResponse disponible = obtenerTenant(usuarioTenant);

           response.setTenantSeleccionado(disponible);

        }  else {
        List<UsuarioTenant> relaciones = obtenerRelacionTenant(usuario);

        if (relaciones.size() == 1) {
            response.setTenantSeleccionado(
                 obtenerTenant(relaciones.getFirst())
            );
        } else {
            response.setTenants(
                 obtenerTenants(relaciones)
            );
        }
    }



        return response;
    }

    public Optional<UsuarioTenant> datosIdentificador(String identificador) {

        return usuarioTenantRepository.findByIdentificadorSce(identificador);
    }

    public List<UsuarioTenant> obtenerRelacionTenant(Usuario usuario){

        return usuarioTenantRepository.findByUsuario(usuario).stream().filter(relacion ->
                relacion.getEstado() == UsuarioTenantEstado.ACTIVE
        ).toList();
    }

    public List<TenantDisponibleResponse> obtenerTenants(List<UsuarioTenant> relaciones){
        //Relaciones

        return relaciones.stream()
                .map(relacion -> {
                    TenantDisponibleResponse dto =
                            new TenantDisponibleResponse();

                    dto.setId(relacion.getTenant().getId());
                    dto.setNombre(relacion.getTenant().getNombre());
                    dto.setRole(relacion.getRole());

                    return dto;
                })
                .toList();
    }
    public TenantDisponibleResponse obtenerTenant(UsuarioTenant relacion) {

        TenantDisponibleResponse dto = new TenantDisponibleResponse();

        dto.setId(relacion.getTenant().getId());
        dto.setNombre(relacion.getTenant().getNombre());
        dto.setRole(relacion.getRole());

        return dto;
    }

    @Transactional(readOnly = true)
    public TenantSeleccionadoResponse seleccionarTenant(Usuario usuario, UUID tenantId) {

        UsuarioTenant usuarioTenant = usuarioTenantRepository
             .findByIdUsuarioIdAndIdTenantId(usuario.getId(), tenantId)
             .orElseThrow(() ->
                  new IllegalStateException(
                       "El usuario no pertenece al tenant seleccionado"
                  )
             );

        if (usuarioTenant.getEstado() != UsuarioTenantEstado.ACTIVE) {
            throw new IllegalStateException(
                 "La relación del usuario con el tenant no está activa"
            );
        }

        if (usuarioTenant.getTenant().getEstado() != TenantEstado.ACTIVE) {
            throw new IllegalStateException(
                 "El tenant no está activo"
            );
        }

        TenantSeleccionadoResponse response = new TenantSeleccionadoResponse();

        response.setTenantId(usuarioTenant.getTenant().getId());
        response.setNombre(usuarioTenant.getTenant().getNombre());
        response.setRole(usuarioTenant.getRole());

        return response;
    }

}
