package LabMouses;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MouseTest {
    private static final LocalDate BIRTH = LocalDate.of(2026, 1, 15);

    private static Chromosome x(boolean mutated) {
        return new Chromosome(ChromosomeType.X, mutated);
    }

    private static Chromosome y(boolean mutated) {
        return new Chromosome(ChromosomeType.Y, mutated);
    }

    private static Mouse male(boolean xMutated, boolean yMutated) {
        return new Mouse("M1", BIRTH, 25, Sex.Male, 37.0f, "healthy", x(xMutated), y(yMutated));
    }

    private static Mouse female(boolean x1Mutated, boolean x2Mutated) {
        return new Mouse("F1", BIRTH, 22, Sex.Female, 37.0f, "healthy", x(x1Mutated), x(x2Mutated));
    }
    @Test
    @DisplayName("Un raton valido guarda todos sus datos")
    void validMouseStoresAllData() {
        Chromosome cr1 = x(false);
        Chromosome cr2 = y(false);
        Mouse m = new Mouse("M1", BIRTH, 25, Sex.Male, 37.5f, "healthy", cr1, cr2);

        assertEquals("M1", m.getMouseId());
        assertEquals(BIRTH, m.getBirthDate());
        assertEquals(25, m.getWeight());
        assertEquals(Sex.Male, m.getSex());
        assertEquals(37.5f, m.getTemperature(), 0.001);
        assertEquals("healthy", m.getObservations());
        assertSame(cr1, m.getCr1());
        assertSame(cr2, m.getCr2());
    }
    @Test
    @DisplayName("El constructor quita espacios al id y a las observaciones")
    void constructorTrimsIdAndObservations() {
        Mouse m = new Mouse("  M1  ", BIRTH, 25, Sex.Male, 37f, "  ok  ", x(false), y(false));

        assertEquals("M1", m.getMouseId());
        assertEquals("ok", m.getObservations());
    }
    @Test
    @DisplayName("Observaciones null se guardan como cadena vacia")
    void nullObservationsBecomeEmpty() {
        Mouse m = new Mouse("M1", BIRTH, 25, Sex.Male, 37f, null, x(false), y(false));

        assertEquals("", m.getObservations());
    }

    @Test
    @DisplayName("Id null lanza excepción")
    void nullIdThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Mouse(null, BIRTH, 25, Sex.Male, 37f, "", x(false), y(false)));
    }

    @Test
    @DisplayName("Id en blanco lanza excepción")
    void blankIdThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Mouse("   ", BIRTH, 25, Sex.Male, 37f, "", x(false), y(false)));
    }

    @Test
    @DisplayName("Fecha de nacimiento null lanza excepción")
    void nullBirthDateThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Mouse("M1", null, 25, Sex.Male, 37f, "", x(false), y(false)));
    }

}
