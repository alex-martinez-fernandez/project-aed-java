package LabMouses;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChromosomeTest {

    @Test
    @DisplayName("Un cromosoma X mutado guarda su tipo y su mutacion")
    void constructorStoresTypeAndMutation() {
        Chromosome x = new Chromosome(ChromosomeType.X, true);

        assertEquals(ChromosomeType.X, x.getType());
        assertTrue(x.isMutated());
    }

    @Test
    @DisplayName("Un cromosoma Y sin mutar guarda su tipo y su mutacion")
    void constructorStoresYNotMutated() {
        Chromosome y = new Chromosome(ChromosomeType.Y, false);

        assertEquals(ChromosomeType.Y, y.getType());
        assertFalse(y.isMutated());
    }

    @Test
    @DisplayName("Un tipo null lanza IllegalArgumentException")
    void nullTypeThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Chromosome(null, false));
    }
}
