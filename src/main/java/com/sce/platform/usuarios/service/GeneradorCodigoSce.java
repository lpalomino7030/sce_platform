package com.sce.platform.usuarios.service;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class GeneradorCodigoSce {

    private static final String PREFIJO = "SCE-";
    private static final String CARACTERES =
            "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    private static final int LONGITUD = 6;

    private final SecureRandom random = new SecureRandom();


    public String generar() {

        StringBuilder codigo = new StringBuilder(PREFIJO);

        for (int i = 0; i < LONGITUD; i++) {
            int indice = random.nextInt(CARACTERES.length());
            codigo.append(CARACTERES.charAt(indice));
        }

        return codigo.toString();
    }

}
