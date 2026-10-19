package com.sce.platform.empresas.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CrearTenantResponse {

    private TenantResponse tenant;

    private UsuarioInicialResponse usuario;

    private String passwordTemporal;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UsuarioInicialResponse {

        private String id;
        private String nombreUsuario;
        private String correo;
        private String nombres;
        private String apellidos;
        private String codigoSce;
        private String identificadorSce;
        private String role;
    }

}
