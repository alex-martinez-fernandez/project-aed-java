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

    //CONSTRUCTOR CASOS VALIDOS

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

    //CONSTRUCTOR:EXCEPCIONES

    @Test
    @DisplayName("Id null lanza excepcion")
    void nullIdThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Mouse(null, BIRTH, 25, Sex.Male, 37f, "", x(false), y(false)));
    }

    @Test
    @DisplayName("Id en blanco lanza excepcion")
    void blankIdThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Mouse("   ", BIRTH, 25, Sex.Male, 37f, "", x(false), y(false)));
    }

    @Test
    @DisplayName("Fecha de nacimiento null lanza excepcion")
    void nullBirthDateThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Mouse("M1", null, 25, Sex.Male, 37f, "", x(false), y(false)));
    }
    @Test
    @DisplayName("Peso 0 lanza excepción")
    void zeroWeightThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Mouse("M1", BIRTH, 0, Sex.Male, 37f, "", x(false), y(false)));
    }

    @Test
    @DisplayName("Peso negativo lanza excepcion")
    void negativeWeightThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Mouse("M1", BIRTH, -5, Sex.Male, 37f, "", x(false), y(false)));
    }

    @Test
    @DisplayName("Peso 1 (límite) es valido")
    void weightOneIsValid() {
        Mouse m = new Mouse("M1", BIRTH, 1, Sex.Male, 37f, "", x(false), y(false));
        assertEquals(1, m.getWeight());
    }

    @Test
    @DisplayName("Sexo null lanza excepcion")
    void nullSexThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Mouse("M1", BIRTH, 25, null, 37f, "", x(false), y(false)));
    }

    @Test
    @DisplayName("Un cromosoma null lanza excepcion")
    void nullChromosomeThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Mouse("M1", BIRTH, 25, Sex.Male, 37f, "", null, y(false)));
        assertThrows(IllegalArgumentException.class,
                () -> new Mouse("M1", BIRTH, 25, Sex.Male, 37f, "", x(false), null));
    }

    @Test
    @DisplayName("Si el primer cromosoma no es X lanza excepcion")
    void firstChromosomeMustBeX() {
        assertThrows(IllegalArgumentException.class,
                () -> new Mouse("M1", BIRTH, 25, Sex.Male, 37f, "", y(false), y(false)));
    }

    //ESTERILIDAD

    @Test
    @DisplayName("Un macho con XX lanza excepcion")
    void maleWithTwoXThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Mouse("M1", BIRTH, 25, Sex.Male, 37f, "", x(false), x(false)));
    }

    @Test
    @DisplayName("Una hembra con XY lanza excepcion")
    void femaleWithYThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Mouse("F1", BIRTH, 25, Sex.Female, 37f, "", x(false), y(false)));
    }

    @Test
    @DisplayName("Macho con X mutado es esteril")
    void maleWithMutatedXIsSterile() {
        assertTrue(male(true, false).isSterile());
    }

    @Test
    @DisplayName("Macho con X sano no es estéril (aunque su Y esté mutado)")
    void maleWithHealthyXIsNotSterile() {
        assertFalse(male(false, true).isSterile());
    }

    @Test
    @DisplayName("Hembra con los dos X mutados es estéril")
    void femaleWithBothXMutatedIsSterile() {
        assertTrue(female(true, true).isSterile());
    }

    @Test
    @DisplayName("Hembra con un solo X mutado no es estéril")
    void femaleWithOneXMutatedIsNotSterile() {
        assertFalse(female(true, false).isSterile());
        assertFalse(female(false, true).isSterile());
    }

    @Test
    @DisplayName("Hembra sin mutaciones no es estéril")
    void healthyFemaleIsNotSterile() {
        assertFalse(female(false, false).isSterile());
    }
    //POLIGAMIA


    @Test
    @DisplayName("Macho con Y mutado es poligamo")
    void maleWithMutatedYIsPolygamous() {
        assertTrue(male(false, true).isPolygamous());
    }

    @Test
    @DisplayName("Macho con Y sano no es poligamo (aunque su X este mutado)")
    void maleWithHealthyYIsNotPolygamous() {
        assertFalse(male(true, false).isPolygamous());
    }

    @Test
    @DisplayName("Una hembra nunca es poligama, aunque tenga los dos X mutados")
    void femaleIsNeverPolygamous() {
        assertFalse(female(true, true).isPolygamous());
    }

    //SETTERS


    @Test
    @DisplayName("setTemperature acepta los limites 30 y 45")
    void temperatureLimitsAreValid() {
        Mouse m = male(false, false);

        m.setTemperature(30.0f);
        assertEquals(30.0f, m.getTemperature(), 0.001);

        m.setTemperature(45.0f);
        assertEquals(45.0f, m.getTemperature(), 0.001);
    }

    @Test
    @DisplayName("setTemperature fuera de rango lanza excepcion y no cambia el valor")
    void temperatureOutOfRangeThrows() {
        Mouse m = male(false, false);

        assertThrows(IllegalArgumentException.class, () -> m.setTemperature(29.9f));
        assertThrows(IllegalArgumentException.class, () -> m.setTemperature(45.1f));
        assertEquals(37.0f, m.getTemperature(), 0.001);
    }

    @Test
    @DisplayName("setWeight valido cambia el peso")
    void setWeightValid() {
        Mouse m = male(false, false);
        m.setWeight(30);
        assertEquals(30, m.getWeight());
    }

    @Test
    @DisplayName("setWeight invalido lanza excepcion y no cambia el peso")
    void setWeightInvalidThrows() {
        Mouse m = male(false, false);

        assertThrows(IllegalArgumentException.class, () -> m.setWeight(0));
        assertEquals(25, m.getWeight());
    }

    @Test
    @DisplayName("setMouseId quita espacios y rechaza ids en blanco")
    void setMouseId() {
        Mouse m = male(false, false);

        m.setMouseId("  M99 ");
        assertEquals("M99", m.getMouseId());

        assertThrows(IllegalArgumentException.class, () -> m.setMouseId(" "));
        assertThrows(IllegalArgumentException.class, () -> m.setMouseId(null));
        assertEquals("M99", m.getMouseId());
    }

    @Test
    @DisplayName("setObservations con null deja cadena vacia")
    void setObservationsNull() {
        Mouse m = male(false, false);

        m.setObservations("sick");
        assertEquals("sick", m.getObservations());

        m.setObservations(null);
        assertEquals("", m.getObservations());
    }
    //TOSTRING

    @Test
    @DisplayName("toString incluye el id, el sexo y el estado genético")
    void toStringContainsInfo() {
        String text = male(true, true).toString();

        assertTrue(text.contains("M1"));
        assertTrue(text.contains("Male"));
        assertTrue(text.contains("sterile: true"));
        assertTrue(text.contains("polygamous: true"));
    }

}
