package LabMouses;
import java.time.LocalDate;

/**
 * Population of laboratory mice, identified by a name, a person in charge and
 * the number of days it has spent in the lab.
 * Mice are stored in a fixed-size array that can hold up to {@link #MAX_MOUSES} mice.
 */
public class Population {

    public static final int MIN_DAYS = 1;
    public static final int MAX_DAYS = 269;
    public static final int MAX_MOUSES = 10000;

    private String name;
    private String responsible;
    private int daysInLab;
    /**
     * Private so the array can only be changed through the methods of this
     * class, which keep the population coherent: no null or repeated mice,
     * at most {@link #MAX_MOUSES}, and {@code numMouses} always matching the
     * mice stored.
     */
    private Mouse[] mice= new Mouse[MAX_MOUSES];
    private int numMouses=0; //not static: each population counts its own mouse


    /**
     * Creates an empty population.
     *
     * @param name        name of the population; must not be {@code null} or blank
     * @param responsible person in charge; must not be {@code null} or blank
     * @param daysInLab   days in the lab, between {@link #MIN_DAYS} and {@link #MAX_DAYS}
     * @throws IllegalArgumentException if any of the values is not valid
     */

    public Population(String name, String responsible, int daysInLab) {
        validateText(name, "The name of the population should be set.");
        validateText(responsible, "The  responsible of the population should be set.");
        validateDays(daysInLab);
        this.name = name.trim();
        this.responsible = responsible.trim();
        this.daysInLab = daysInLab;
    }

    /**
     * Adds a mouse at the first free position of the population and prints
     * a confirmation message.
     *
     * @param mouse mouse to add; must not be {@code null}
     * @throws IllegalArgumentException       if the mouse is {@code null} or there is
     *                                        already a mouse with the same reference code
     * @throws ArrayIndexOutOfBoundsException if the population already holds
     *                                        {@link #MAX_MOUSES} mice
     */
    public void addMouse(Mouse mouse){
        if(mouse==null){
            throw new IllegalArgumentException("The mouse can't be null");
        }
        if (numMouses== MAX_MOUSES) {
            throw new ArrayIndexOutOfBoundsException("The maximum number of mouse in the lab is " + MAX_MOUSES +
                    "you've surpassed the limit");
        }if (findMouse(mouse.getMouseId()) != -1){
            throw new IllegalArgumentException("The mouse with this id is already present in the lab");
        }
        mice[numMouses] = mouse;
        numMouses++;
        System.out.println( mouse + " mouse has been added to the lab.");
    }

    /**
     * Removes a mouse from the population. The last mouse of the array is moved
     * to the freed position, so the order of the mice may change.
     *
     * @param mouseId reference code of the mouse to remove
     * @throws IllegalArgumentException if there is no mouse with that reference code
     */
    public void deleteMouse(String mouseId){
        int pos = findMouse(mouseId);
        if (pos == -1){
            throw new IllegalArgumentException("There is no mouses with this id in the lab");
        }
        mice[pos] = mice[numMouses-1];
        mice[numMouses-1] = null;
        numMouses--;
    }

    /**
     * Returns the mouse with the given reference code.
     *
     * @param mouseId reference code of the mouse
     * @return the mouse with that reference code
     * @throws IllegalArgumentException if there is no mouse with that reference code
     */
    Mouse getMouse( String mouseId){
        int pos = findMouse(mouseId);
        if (pos == -1){
            throw new IllegalArgumentException("There is no mouses with this id in the lab");
        }
        return mice[pos];
    }




    /**
     * Changes the reference code of a mouse of the population, checking that
     * the new code is not used by another mouse. Changing it to the same code
     * it already has is allowed.
     *
     * @param oldId current reference code of the mouse
     * @param newId new reference code; must not be {@code null} or blank
     * @throws IllegalArgumentException if there is no mouse with {@code oldId},
     *                                  if another mouse already uses {@code newId},
     *                                  or if {@code newId} is {@code null} or blank
     */
    public void changeMouseId(String oldId, String newId){
        Mouse mouse = getMouse(oldId);
        if (newId != null && findMouse(newId) != -1 && !newId.trim().equals(mouse.getMouseId())) {
            throw new IllegalArgumentException("There is already a mouse with the id " + newId);
        }
        mouse.setMouseId(newId);
    }

    /**
     * Returns the reference codes of the mice in the population. The array is
     * not received as a parameter because it is already an attribute of the class.
     *
     * @return a new array with the reference codes; its length is the number
     *         of mice (empty if there are none)
     */
    public String[] getIds(){
        String[] ids = new String[numMouses];
        for (int i=0; i<numMouses; i++){
            ids[i] = mice[i].getMouseId();
        }
        return ids;
    }


    /**
     * Returns the mice of the population, without the empty positions of the
     * internal array. It is a copy, so mice can't be added or removed from
     * outside the class.
     *
     * @return a new array with the mice; its length is the number of mice
     *         (empty if there are none)
     */
    public Mouse[] getMousePopulation(){
        Mouse[] population = new Mouse[numMouses];
        for (int i=0; i<numMouses; i++){
            population[i] = mice[i];
        }
        return population;
    }

    //BUENO BONITO Y BARATO implementacion unicamente por motivos de aprendizaje
    private int findMouse(String mouseId) {
        if (mouseId == null || mouseId.isBlank()) {
            return -1;
        }
        mouseId = mouseId.trim();

        // Sentinel: a mouse with the searched code, after the last real mouse
        mice[numMouses] = new Mouse(mouseId, LocalDate.now(), 1, Sex.Male, 37.0f, "",
                new Chromosome(ChromosomeType.X, false), new Chromosome(ChromosomeType.Y, false));

        int i = 0;
        while (!mice[i].getMouseId().equals(mouseId)) {
            i++;
        }

        mice[numMouses] = null; // remove the sentinel

        if (i == numMouses) {
            return -1; // only the sentinel was found
        }
        return i;
    }
    // SEARCH ALGORITHMS STUDIED
// Sequential search (previous version): 5*n operations -> O(n)
// Sequential search with sentinel: the searched value is added at the end -> 3*n operations -> O(n) (the one used)
// Binary search: O(log(n)), but the mice must be sorted. Since we can't ensure that,
// we use the sequential search with sentinel.

    private static void validateText(String text, String field) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException(field + " cannot  be empty");
        }
    }

    private static void validateDays(int days) {
        if (days < MIN_DAYS || days > MAX_DAYS) {
            throw new IllegalArgumentException(
                    "The days in the lab should be between " + MIN_DAYS + " and " + MAX_DAYS);
        }
    }
}