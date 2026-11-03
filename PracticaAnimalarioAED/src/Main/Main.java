package Main;
import LabMouses.*;

import LabMouses.Population;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static Scanner input = new Scanner(System.in);
    private static Population population = null;


    public static void main(String[] args) {
        int option;
        do {
            option = readInt("Choose an option: ");
            switch (option) {
                case 0 -> System.out.println("Goodbye!");
                case 1 -> openPopulation();
                case 2 -> createPopulation();
                case 3 -> addMouse();
                case 4 -> listMice();
                case 5 -> deleteMouse();
                case 6 -> modifyMouse();
                case 7 -> showMouse();
                case 8 -> save();
                case 9 -> saveAs();
                default -> System.out.println("Invalid option.");
            }
        } while (option != 0);
    }

            private static void printmenu () {
                System.out.println();
                System.out.println("LAB MOUSES MENU");
                System.out.println();
                System.out.println("manage your mouse lab like a pro");
                if (population == null) {
                    System.out.println("No active population");
                } else {

                }

                System.out.println("0. Exit");
                System.out.println("1. Open population");
                System.out.println("2. Create population");
                System.out.println("3. Add mouse");
                System.out.println("4. List mice");
                System.out.println("5. Delete mouse");
                System.out.println("6. Modify mouse");
                System.out.println("7. Show mouse");
                System.out.println("8. Save");
                System.out.println("9. Save as");
            }


    private static void createPopulation() {
        System.out.print("Name: ");
        String name = input.nextLine();
        System.out.print("Person in charge: ");
        String responsible = input.nextLine();
        int days = readInt("Days in the lab: ");
        try {
            population = new Population(name, responsible, days);
            System.out.println("Population created.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private static void addMouse() {
        if (population == null) {
            System.out.println("There is no active population.");
            return;
        }
        try {
            System.out.print("Reference code: ");
            String id = input.nextLine();
            System.out.print("Birth date (YYYY-MM-DD): ");
            LocalDate birthDate = LocalDate.parse(input.nextLine());

            int weight = readInt("Weight (g): ");
            System.out.print("Temperature (ºC): ");

            float temperature = Float.parseFloat(input.nextLine());
            System.out.print("Observations: ");
            String observations = input.nextLine();
            System.out.print("Sex (M/F): ");
            Sex sex = input.nextLine().equalsIgnoreCase("M") ? Sex.Male : Sex.Female;

            System.out.print("Is the first X mutated? (y/n): ");
            Chromosome cr1 = new Chromosome(ChromosomeType.X, input.nextLine().equalsIgnoreCase("y"));

            System.out.print("Is the second chromosome mutated? (y/n): ");
            ChromosomeType secondType = (sex == Sex.Male) ? ChromosomeType.Y : ChromosomeType.X;

            Chromosome cr2 = new Chromosome(secondType, input.nextLine().equalsIgnoreCase("y"));

            Mouse mouse = new Mouse(id, birthDate, weight, sex, temperature, observations, cr1, cr2);
            population.addMouse(mouse);

        } catch (Exception e) {
            System.out.println("Mouse not added: " + e.getMessage());
        }
    }
    private static void listMice() {
        if (population == null) {
            System.out.println("There is no active population.");
            return;
        }
        String[] ids = population.getIds(population.mice);
        if (ids.length == 0) {
            System.out.println("The population has no mice.");
        }
        for (String id : ids) {
            System.out.println(" - " + id);
        }
    }

    private static void deleteMouse() {
    }

    private static void modifyMouse() {
    }

    private static void showMouse() {
    }

    private static void save() {
    }

    private static void saveAs() {
    }
    private static void openPopulation() {

    }
    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer number.");
            }
        }
    }
}
