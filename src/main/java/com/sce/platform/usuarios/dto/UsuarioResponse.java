package com.sce.platform.usuarios.dto;

import com.sce.platform.usuarios.enums.UsuarioEstado;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioResponse {
    private UUID id;
    private String nombreUsuario;
    private String correo;
    private String nombres;
    private String apellidos;
    private UsuarioEstado estado;
    private Instant fechaCreacion;
    private Instant fechaActualizacion;
}
