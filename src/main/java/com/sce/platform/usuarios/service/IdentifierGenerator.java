package com.sce.platform.usuarios.service;
import org.springframework.stereotype.Component;
@Component
public class IdentifierGenerator {
    public String generar(String usuario, String tenant) {
        return usuario + "@" + tenant;
    }
}