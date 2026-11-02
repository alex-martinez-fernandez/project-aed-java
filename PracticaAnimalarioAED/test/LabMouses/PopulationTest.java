package LabMouses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PopulationTest {
    private Population population;

    @BeforeEach
    void setUp() {
        population = new Population("Pop A", "Dr. Smith", 30);
    }

    private static Mouse mouse(String id) {
        return new Mouse(id, LocalDate.of(2026, 1, 1), 25, Sex.Male, 37f, "",
                new Chromosome(ChromosomeType.X, false),
                new Chromosome(ChromosomeType.Y, false));
    }
    //CONSTRUCTOR

    @Test
    @DisplayName("Una poblacion con datos validos se crea sin errores")
    void validPopulation() {
        assertDoesNotThrow(() -> new Population("Pop", "Ana", 100));
    }

    @Test
    @DisplayName("Los dias límite 1 y 269 son validos")
    void daysLimitsAreValid() {
        assertDoesNotThrow(() -> new Population("Pop", "Ana", Population.MIN_DAYS));
        assertDoesNotThrow(() -> new Population("Pop", "Ana", Population.MAX_DAYS));
    }

    @Test
    @DisplayName("Dias fuera de rango (0 y 270) lanzan excepción")
    void daysOutOfRangeThrow() {
        assertThrows(IllegalArgumentException.class, () -> new Population("Pop", "Ana", 0));
        assertThrows(IllegalArgumentException.class, () -> new Population("Pop", "Ana", 270));
    }

    @Test
    @DisplayName("Nombre null o en blanco lanza excepcion")
    void invalidNameThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Population(null, "Ana", 10));
        assertThrows(IllegalArgumentException.class, () -> new Population("  ", "Ana", 10));
    }

    @Test
    @DisplayName("Responsable null o en blanco lanza excepcion")
    void invalidResponsibleThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Population("Pop", null, 10));
        assertThrows(IllegalArgumentException.class, () -> new Population("Pop", "", 10));
    }

    //AddMouse y getMouse

    @Test
    @DisplayName("Un raton añadido se puede recuperar por su id")
    void addedMouseCanBeRetrieved() {
        Mouse m = mouse("M1");
        population.addMouse(m);

        assertSame(m, population.getMouse("M1"));
    }

    @Test
    @DisplayName("Añadir un raton null lanza excepcion")
    void addNullMouseThrows() {
        assertThrows(IllegalArgumentException.class, () -> population.addMouse(null));
    }

    @Test
    @DisplayName("Añadir dos ratones con el mismo id lanza excepcion")
    void addDuplicateIdThrows() {
        population.addMouse(mouse("M1"));

        assertThrows(IllegalArgumentException.class, () -> population.addMouse(mouse("M1")));
        assertEquals(1, population.getIds(population.mice).length);
    }

    @Test
    @DisplayName("Se pueden añadir MAX_MOUSES ratones, pero no uno mas")
    void cannotExceedMaxMice() {
        for (int i = 0; i < Population.MAX_MOUSES; i++) {
            population.addMouse(mouse("M" + i));
        }

        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> population.addMouse(mouse("extra")));
    }

    @Test
    @DisplayName("getMouse con un id inexistente o null lanza excepcion")
    void getMissingMouseThrows() {
        assertThrows(IllegalArgumentException.class, () -> population.getMouse("nope"));
        assertThrows(IllegalArgumentException.class, () -> population.getMouse(null));
    }
    //DELETE

    @Test
    @DisplayName("Borrar un raton lo elimina y deja a los demas")
    void deleteRemovesOnlyThatMouse() {
        population.addMouse(mouse("M1"));
        population.addMouse(mouse("M2"));
        population.addMouse(mouse("M3"));

        population.deleteMouse("M1");

        assertThrows(IllegalArgumentException.class, () -> population.getMouse("M1"));
        assertNotNull(population.getMouse("M2"));
        assertNotNull(population.getMouse("M3"));
        assertEquals(2, population.getIds(population.mice).length);
    }

    @Test
    @DisplayName("Borrar el ultimo raton deja la poblacion vacia")
    void deleteLastMouse() {
        population.addMouse(mouse("M1"));
        population.deleteMouse("M1");

        assertEquals(0, population.getIds(population.mice).length);
    }

    @Test
    @DisplayName("Borrar un id inexistente lanza excepcion")
    void deleteMissingMouseThrows() {
        assertThrows(IllegalArgumentException.class, () -> population.deleteMouse("nope"));
    }

    @Test
    @DisplayName("Tras borrar un raton se puede volver a añadir uno con ese id")
    void canReAddAfterDelete() {
        population.addMouse(mouse("M1"));
        population.deleteMouse("M1");

        assertDoesNotThrow(() -> population.addMouse(mouse("M1")));
    }
    //ChangeMouseId

    @Test
    @DisplayName("changeMouseId cambia el id: el nuevo existe y el viejo no")
    void changeIdWorks() {
        population.addMouse(mouse("M1"));

        population.changeMouseId("M1", "M10");

        assertEquals("M10", population.getMouse("M10").getMouseId());
        assertThrows(IllegalArgumentException.class, () -> population.getMouse("M1"));
    }

    @Test
    @DisplayName("changeMouseId a un id que ya usa otro raton lanza excepcion")
    void changeToExistingIdThrows() {
        population.addMouse(mouse("M1"));
        population.addMouse(mouse("M2"));

        assertThrows(IllegalArgumentException.class, () -> population.changeMouseId("M1", "M2"));
        assertNotNull(population.getMouse("M1")); // no ha cambiado
    }

    @Test
    @DisplayName("changeMouseId al mismo id no lanza excepcion")
    void changeToSameIdIsAllowed() {
        population.addMouse(mouse("M1"));

        assertDoesNotThrow(() -> population.changeMouseId("M1", "M1"));
    }

    @Test
    @DisplayName("changeMouseId con un id viejo inexistente lanza excepcion")
    void changeMissingIdThrows() {
        assertThrows(IllegalArgumentException.class, () -> population.changeMouseId("nope", "M5"));
    }

    @Test
    @DisplayName("changeMouseId con id nuevo null o en blanco lanza excepcion")
    void changeToInvalidIdThrows() {
        population.addMouse(mouse("M1"));

        assertThrows(IllegalArgumentException.class, () -> population.changeMouseId("M1", null));
        assertThrows(IllegalArgumentException.class, () -> population.changeMouseId("M1", " "));
    }
    //getIds and GetMousePopulation

    @Test
    @DisplayName("Una población vacía devuelve arrays vacios")
    void emptyPopulationReturnsEmptyArrays() {
        assertEquals(0, population.getIds(population.mice).length);
        assertEquals(0, population.getMousePopulation(population.mice).length);
    }

    @Test
    @DisplayName("getIds devuelve los ids en orden de insercion")
    void getIdsReturnsAllIds() {
        population.addMouse(mouse("M1"));
        population.addMouse(mouse("M2"));

        assertArrayEquals(new String[]{"M1", "M2"}, population.getIds(population.mice));
    }

    @Test
    @DisplayName("getMousePopulation devuelve exactamente los ratones añadidos")
    void getMousePopulationReturnsAllMice() {
        Mouse m1 = mouse("M1");
        Mouse m2 = mouse("M2");
        population.addMouse(m1);
        population.addMouse(m2);

        Mouse[] result = population.getMousePopulation(population.mice);

        assertEquals(2, result.length);
        assertSame(m1, result[0]);
        assertSame(m2, result[1]);
    }



}
