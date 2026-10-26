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
    private static int numMouses=0;

    private String name;
    private String responsible;
    private int daysInLab;
    Mouse[] mice= new Mouse[MAX_MOUSES];


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
    void addMouse(Mouse mouse){
        if (numMouses== mice.length) {
            throw new ArrayIndexOutOfBoundsException("The maximum number of mouse in the lab is " + MAX_MOUSES +
                    "you've surpassed the limit");
        }else{
            mice[numMouses] = mouse;
            numMouses++;
            System.out.println( mouse + " mouse has been added to the lab.");
        }

    }

    /**
     * Removes a mouse from the population.
     *
     * @param mouseId position of the mouse in the array
     * @throws ArrayIndexOutOfBoundsException if the population has no mice
     */
    void deleteMouse(int mouseId){
        if (numMouses== 0) {
            throw new ArrayIndexOutOfBoundsException("You have no mouses in your population.");
        }
        if (numMouses==1) {
            mice[0] = null;
            numMouses=0;
            System.out.println(" the only mouse has been deleted from the lab.");
        }else{
            mice[mouseId] = null; //debemos comprobar que el mouse del id esta en el array
            numMouses--;
        }
    }

    /**
     * Returns the mouse stored at the given position.
     *
     * @param mouseId position of the mouse in the array
     * @return the mouse at that position, or {@code null} if the position is empty
     */
    Mouse getMouse( int mouseId){
        if (mice [mouseId] == null) {
            throw new ArrayIndexOutOfBoundsException("No mouse with the id " + mouseId + " has been added to the lab.");
        }
        return mice[mouseId];}

    /**
     * Returns the identifiers of the mice in the population.
     *
     * @return array with the identifiers of the mice
     */
    int[] getIds {return ;}

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