package LabMouses;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static Scanner input = new Scanner(System.in);
    private static Population population = null;


    public static void main(String[] args) {
        int option;
        do {
            printmenu();
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
                System.out.println("MANAGE YOUR MOUSES LIKE A PRO");
                if (population == null) {
                    System.out.println("No active population");
                } else {
                    System.out.println("Active population: " + population.getIds().length + " mice");
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

    private static void openPopulation() {
        System.out.println("Pending implementation.");
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
            return;//sale del metodo
        }
        String[] ids = population.getIds();
        if (ids.length == 0) {
            System.out.println("The population has no mice.");
        }
        for (String id : ids) {
            System.out.println(" - " + id);
        }
    }
    public static void deleteMouse() {
        if (population == null) {
            System.out.println("There is no active population.");
            return;
        }
        if (population.getIds().length == 0) {
            System.out.println("The population has no mice.");
            return;
        }
        System.out.print("Reference code of the mouse to delete: ");
        String id = input.nextLine().trim();
        try {
            population.deleteMouse(id);
            System.out.println("Mouse " + id + " deleted.");
        } catch (IllegalArgumentException e) {
            // deleteMouse throws it when the id doesn't exist: nothing is changed
            System.out.println("There is no mouse with the reference " + id + ".");
        }
    }

    private static void modifyMouse() {
        if (population == null) {
            System.out.println("There is no active population.");
            return;
        }
        if (population.getIds().length == 0) {
            System.out.println("The population has no mice.");
            return;
        }
        System.out.print("Reference code of the mouse to modify: ");
        String id = input.nextLine().trim();
        Mouse mouse;
        try {
            mouse = population.getMouse(id);
        } catch (IllegalArgumentException e) {
            System.out.println("There is no mouse with the reference " + id + ".");
            return;
        }

        int option;
        do {
            System.out.println();
            System.out.println("Modifying " + mouse);
            System.out.println("1. Reference code");
            System.out.println("2. Weight");
            System.out.println("3. Temperature");
            System.out.println("4. Observations");
            System.out.println("5. Birth date");
            System.out.println("6. Sex and chromosomes");
            System.out.println("0. Back");
            option = readInt("Choose an option: ");

            // Each new value is validated before the mouse is changed,
            // so an invalid value never leaves the mouse in an incoherent state.
            try {
                switch (option) {
                    case 0 -> System.out.println("Back to the main menu.");
                    case 1 -> {
                        System.out.print("New reference code: ");
                        String newId = input.nextLine().trim();
                        population.changeMouseId(mouse.getMouseId(), newId);
                        System.out.println("Reference changed.");
                    }
                    case 2 -> {
                        mouse.setWeight(readInt("New weight (g): "));
                        System.out.println("Weight changed.");
                    }
                    case 3 -> {
                        System.out.print("New temperature (ºC): ");
                        mouse.setTemperature(Float.parseFloat(input.nextLine()));
                        System.out.println("Temperature changed.");
                    }
                    case 4 -> {
                        System.out.print("New observations: ");
                        mouse.setObservations(input.nextLine());
                        System.out.println("Observations changed.");
                    }
                    case 5 -> {
                        // birthDate is final: a new mouse is created with the new date
                        System.out.print("New birth date (YYYY-MM-DD): ");
                        LocalDate newDate = LocalDate.parse(input.nextLine());
                        Mouse newMouse = new Mouse(mouse.getMouseId(), newDate, mouse.getWeight(),
                                mouse.getSex(), mouse.getTemperature(), mouse.getObservations(),
                                mouse.getCr1(), mouse.getCr2());
                        mouse = replaceMouse(mouse, newMouse);
                        System.out.println("Birth date changed.");
                    }
                    case 6 -> {
                        // sex and chromosomes are final: a new mouse is created.
                        // The chromosomes are asked again so they match the new sex (XY or XX).
                        System.out.print("New sex (M/F): ");
                        Sex newSex = input.nextLine().equalsIgnoreCase("M") ? Sex.Male : Sex.Female;
                        System.out.print("Is the first X mutated? (y/n): ");
                        Chromosome cr1 = new Chromosome(ChromosomeType.X, input.nextLine().equalsIgnoreCase("y"));
                        System.out.print("Is the second chromosome mutated? (y/n): ");
                        ChromosomeType secondType = (newSex == Sex.Male) ? ChromosomeType.Y : ChromosomeType.X;
                        Chromosome cr2 = new Chromosome(secondType, input.nextLine().equalsIgnoreCase("y"));
                        Mouse newMouse = new Mouse(mouse.getMouseId(), mouse.getBirthDate(), mouse.getWeight(),
                                newSex, mouse.getTemperature(), mouse.getObservations(), cr1, cr2);
                        mouse = replaceMouse(mouse, newMouse);
                        System.out.println("Sex and chromosomes changed.");
                    }
                    default -> System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                // wrong formats (number, date) and the validations of Mouse/Population
                System.out.println("Change rejected: " + e.getMessage());
            }
        } while (option != 0);
    }
    private static Mouse replaceMouse(Mouse oldMouse, Mouse newMouse) {
        population.deleteMouse(oldMouse.getMouseId());
        population.addMouse(newMouse);
        return newMouse;
    }

    private static void showMouse() {
        if (population == null) {
            System.out.println("There is no active population.");
            return;
        }
        if (population.getIds().length == 0) {
            System.out.println("The population has no mice.");
            return;
        }
        System.out.print("Reference code: ");
        String id = input.nextLine().trim();
        try {
            Mouse m = population.getMouse(id);
            System.out.println("Reference:    " + m.getMouseId());
            System.out.println("Birth date:   " + m.getBirthDate());
            System.out.println("Weight:       " + m.getWeight() + " g");
            System.out.println("Sex:          " + m.getSex());
            System.out.println("Temperature:  " + m.getTemperature() + " ºC");
            System.out.println("Observations: " + m.getObservations());
            System.out.println("Chromosome 1: " + m.getCr1().getType() + (m.getCr1().isMutated() ? " (mutated)" : " (not mutated)"));
            System.out.println("Chromosome 2: " + m.getCr2().getType() + (m.getCr2().isMutated() ? " (mutated)" : " (not mutated)"));
            System.out.println("Sterile:      " + (m.isSterile() ? "yes" : "no"));
            System.out.println("Polygamous:   " + (m.isPolygamous() ? "yes" : "no"));
        } catch (IllegalArgumentException e) {
            // getMouse throws it when the id doesn't exist
            System.out.println("There is no mouse with the reference " + id + ".");
        }
    }

    private static void save() {
        System.out.println("Pending implementation.");
    }

    private static void saveAs() {
        System.out.println("Pending implementation.");
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
