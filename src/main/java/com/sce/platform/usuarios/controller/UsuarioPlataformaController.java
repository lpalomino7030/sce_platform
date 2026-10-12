package com.sce.platform.usuarios.controller;

import com.sce.platform.usuarios.service.UsuarioPlataformaService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioPlataformaController {

    private final UsuarioPlataformaService usuarioPlataformaService;

    public UsuarioPlataformaController(UsuarioPlataformaService usuarioPlataformaService) {
        this.usuarioPlataformaService = usuarioPlataformaService;
    }
}
