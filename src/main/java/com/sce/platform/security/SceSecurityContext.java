package com.sce.platform.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SceSecurityContext {
    private SceSecurityContext() {
    }

    public static SceAuthentication getAuthentication() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new IllegalStateException(
                    "No existe una autenticación en el contexto de seguridad"
            );
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof SceAuthentication sceAuthentication)) {
            throw new IllegalStateException(
                    "El principal no es una SceAuthentication"
            );
        }

        return sceAuthentication;
    }
}
