package com.sce.platform.auth.dto;

import com.sce.platform.usuarios.entity.Usuario;
import com.sce.platform.usuarios.entity.UsuarioTenant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResultadoAutenticacion {
    private Usuario usuario;
    private UsuarioTenant usuarioTenant;
}
