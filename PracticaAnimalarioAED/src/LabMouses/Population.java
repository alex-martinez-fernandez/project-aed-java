package LabMouses;

import java.util.Arrays;

public class Population {

    public static final int MIN_DAYS = 1;
    public static final int MAX_DAYS = 269;
    public static final int MAX_MOUSES = 10000;
    private static int numMouses=0;

    private String name;
    private String responsible;
    private int daysInLab;
    Mouse[] mice= new Mouse[MAX_MOUSES];


    public Population(String name, String responsible, int daysInLab) {
        validateText(name, "The name of the population should be set.");
        validateText(responsible, "The  responsible of the population should be set.");
        validateDays(daysInLab);
        this.name = name.trim();
        this.responsible = responsible.trim();
        this.daysInLab = daysInLab;
    }

    void addMouse(Mouse mouse){
        if (numMouses== mice.length) {
            throw new ArrayIndexOutOfBoundsException("The maximum number of mouse in the lab is " + MAX_MOUSES +
                    "you've surpassed the limit")
        }else{
            mice[numMouses] = mouse;
            numMouses++;
            System.out.println( mouse + " mouse has been added to the lab.");
        }

    }

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

    Mouse getMouse( int mouseId){
        return mice[mouseId];}

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
