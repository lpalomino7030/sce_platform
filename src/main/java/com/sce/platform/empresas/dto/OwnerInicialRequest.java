package com.sce.platform.empresas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OwnerInicialRequest {
    @NotBlank
    @Size(max = 50)
    private String nombreUsuario;

    private String correo;

    @Size(max = 100)
    private String nombres;

    @Size(max = 100)
    private String apellidos;
}
