package com.sce.platform.usuarios.service;
import org.springframework.stereotype.Component;
@Component
public class GenerateIdentificador {
    public String generar(String usuario, String tenant) {
        return usuario + "@" + tenant;
    }
}