package LabMouses;


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
    Mouse[] mice= new Mouse[MAX_MOUSES];
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
     * @param mouse mouse to add
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
     * Removes a mouse from the population.
     *
     * @param mouseId position of the mouse in the array
     * @throws ArrayIndexOutOfBoundsException if the population has no mice
     */
    public void deleteMouse(String mouseId){
        int pos = findMouse(mouseId);
        if (pos != -1){
            throw new IllegalArgumentException("There is no mouses with this id in the lab");
        }
        mice[pos] = mice[numMouses-1];
        mice[numMouses-1] = null;
        numMouses--;
        }

    /**
     * Returns the mouse stored at the given position.
     *
     * @param mouseId position of the mouse in the array
     * @return the mouse at that position, or {@code null} if the position is empty
     */
    Mouse getMouse( int mouseId){
        int pos = findMouse(mouseId);
        if (pos != -1){
            throw new IllegalArgumentException("There is no mouses with this id in the lab");
        }
        return mice[pos];
    }




    public void changeMouseId(String oldId, String newId){
        Mouse mouse = getMouse(oldId);
        if (newId != null && findMouse(newId) != -1 && !newId.trim().equals(mouse.getMouseId())) {
            throw new IllegalArgumentException("There is already a mouse with the id " + newId);
        }
        mouse.setMouseId(newId);
    }

    /**
     * Returns the identifiers of the mice in the population.
     *
     * @return array with the identifiers of the mice
     */
    public static String[] getIds( Mouse [] mice){
        String[] ids = new String[MAX_MOUSES];
        for (int i=0; i<MAX_MOUSES; i++){
            ids[i] = mice[i].getMouseId();
        }
        return ids;
    }
    //tried to make the validation of the string mouseId that if is changed it has to be unique
    public static  Mouse[] getMousePopulation(Mouse [] mice){
        Mouse[] population = new Mouse[MAX_MOUSES];
        for (int i=0; i<MAX_MOUSES; i++){
            population[i] = mice[i];
        }
        return population;
    }
//busca un reton por su Id y devuelve en que posicion de array esta
    private int findMouse(int mouseId){
        if (mouseId == null){
            return -1;
        }
        for (int i=0; i<numMouses; i++){
            if(mice[i].getMouseId().equals(mouseId)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks that a text is not {@code null} or blank.
     *
     * @param text  text to check
     * @param field message used to build the error
     * @throws IllegalArgumentException if the text is {@code null} or blank
     */
    private static void validateText(String text, String field) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException(field + " cannot  be empty");
        }
    }

    /**
     * Checks that a number of days is between {@link #MIN_DAYS} and {@link #MAX_DAYS}.
     *
     * @param days number of days to check
     * @throws IllegalArgumentException if it is out of range
     */
    private static void validateDays(int days) {
        if (days < MIN_DAYS || days > MAX_DAYS) {
            throw new IllegalArgumentException(
                    "The days in the lab should be between " + MIN_DAYS + " and " + MAX_DAYS);
        }
    }

}
//aun se debe hacer el metodo para cambiar el mouseID