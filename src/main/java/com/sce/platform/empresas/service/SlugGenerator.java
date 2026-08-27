package com.sce.platform.empresas.service;

import org.springframework.stereotype.Component;

@Component
public class SlugGenerator {

    public String generar(String nombre) {

        String[] especiales = {"á","é","í","ó","ú", "ñ", ".", "Á","É", "Í","Ó","Ú"};
        // normalización
        String nomalizacion = nombre.toLowerCase().trim();


        return nomalizacion;
    }

}
