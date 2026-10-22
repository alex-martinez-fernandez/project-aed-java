package LabMouses;

public class Population {

    public static final int MIN_DAYS = 1;
    public static final int MAX_DAYS = 269;

    private String name;
    private String responsible;
    private int daysInLab;
    Mouse[] mice;

    public Population(String name, String responsible, int daysInLab) {
        validateText(name, "The name of the population should be set.");
        validateText(responsible, "The  responsible of the population should be set.");
        validateDays(daysInLab);
        this.name = name.trim();
        this.responsible = responsible.trim();
        this.daysInLab = daysInLab;
    }

    void addMouse(Mouse mouse){}//exception in both 3
    void deleteMouse(Mouse mouse){}

    Mouse getMouse( int mouseId){return mice[mouseId];}

    int[] getIds{return ;}

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
