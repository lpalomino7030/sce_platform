package com.sce.platform.empresas.service;

import com.sce.platform.empresas.service.SlugGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SlugGeneratorTest {
    private final SlugGenerator slugGenerator = new SlugGenerator();

    @Test
    void deberiaGenerarSlug() {

        String resultado = slugGenerator.generar("Torque G46");

        assertEquals("torqueg46", resultado);
    }

}
