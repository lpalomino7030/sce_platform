package com.sce.platform.security;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordGenerator {
    private static final String CARACTERES =
         "ABCDEFGHJKLMNPQRSTUVWXYZ"
              + "abcdefghijkmnopqrstuvwxyz"
              + "23456789"
              + "!@#$%";

    private static final int LONGITUD = 12;

    private final SecureRandom secureRandom = new SecureRandom();

    public String generar() {

        StringBuilder password = new StringBuilder(LONGITUD);

        for (int i = 0; i < LONGITUD; i++) {
            int indice = secureRandom.nextInt(CARACTERES.length());
            password.append(CARACTERES.charAt(indice));
        }

        return password.toString();
    }

}
