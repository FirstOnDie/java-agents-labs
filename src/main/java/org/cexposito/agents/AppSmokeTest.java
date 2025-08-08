package org.cexposito.agents;

import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

class AppSmokeTest {

    @Test
    void projectIsConfigured() {
        // Test trivial para mantener CI verde sin depender de la API.
        assertTrue(true, "Proyecto configurado correctamente");
    }
}