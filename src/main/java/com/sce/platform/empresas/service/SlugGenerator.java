package com.sce.platform.empresas.service;

import org.springframework.stereotype.Component;

import java.text.Normalizer;

@Component
public class SlugGenerator {

    public String generar(String nombre) {

        String normalizacion = nombre.toLowerCase().trim();

        normalizacion = Normalizer.normalize(
                normalizacion,
                Normalizer.Form.NFD
        );
        normalizacion = normalizacion.replaceAll("\\p{M}", "");

        normalizacion = normalizacion.replaceAll("[^a-z0-9]", "");

        return normalizacion;
    }

}
