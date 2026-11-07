package LabMouses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.time.LocalDate;


public class Main {
    //implementation buffered reader in the program

    private static final BufferedReader input =
            new BufferedReader(new InputStreamReader(System.in));
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
        String name = readLine("Name: ");
        String responsible = readLine("Person in charge: ");
        int days = readInt("Days in the lab: ");
        try {
            population = new Population(name, responsible, days);
            System.out.println("Population created.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    /**
     * Asks for the data and mutations of a mouse and adds it to the active
     * population. The second chromosome is created as Y or X depending on the
     * sex, so the user can't build an invalid configuration. If any value is
     * invalid or the reference is already used, the mouse is not added.
     */
    private static void addMouse() {
        if (population == null) {
            System.out.println("There is no active population.");
            return;
        }
        try {
            String id = readLine("Reference code: ");
            LocalDate birthDate = LocalDate.parse(readLine("Birth date (YYYY-MM-DD): "));

            int weight = readInt("Weight (g): ");

            float temperature = Float.parseFloat(readLine("Temperature (ºC): "));
            String observations = readLine("Observations: ");
            Sex sex = readLine("Sex (M/F): ").equalsIgnoreCase("M") ? Sex.Male : Sex.Female;

            Chromosome cr1 = new Chromosome(ChromosomeType.X,
                    readLine("Is the first X mutated? (y/n): ").equalsIgnoreCase("y"));

            ChromosomeType secondType = (sex == Sex.Male) ? ChromosomeType.Y : ChromosomeType.X;
            Chromosome cr2 = new Chromosome(secondType,
                    readLine("Is the second chromosome mutated? (y/n): ").equalsIgnoreCase("y"));

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
        String id = readLine("Reference code of the mouse to delete: ").trim();
        try {
            population.deleteMouse(id);
            System.out.println("Mouse " + id + " deleted.");
        } catch (IllegalArgumentException e) {
            // deleteMouse throws it when the id doesn't exist: nothing is changed
            System.out.println("There is no mouse with the reference " + id + ".");
        }
    }

    /**
     * Asks for a reference code and opens a submenu to modify the data of that
     * mouse: reference code, weight, temperature, observations, birth date, or
     * sex and chromosomes. The submenu is shown until the user chooses 0.
     * Every new value is validated before changing the mouse, so an invalid
     * change is rejected and the mouse keeps its previous data. When the sex
     * changes, the chromosomes are asked again so they match the new sex.
     */
    private static void modifyMouse() {
        if (population == null) {
            System.out.println("There is no active population.");
            return;
        }
        if (population.getIds().length == 0) {
            System.out.println("The population has no mice.");
            return;
        }
        String id = readLine("Reference code of the mouse to modify: ").trim();
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
                        String newId = readLine("New reference code: ").trim();
                        population.changeMouseId(mouse.getMouseId(), newId);
                        System.out.println("Reference changed.");
                    }
                    case 2 -> {
                        mouse.setWeight(readInt("New weight (g): "));
                        System.out.println("Weight changed.");
                    }
                    case 3 -> {
                        mouse.setTemperature(Float.parseFloat(readLine("New temperature (ºC): ")));
                        System.out.println("Temperature changed.");
                    }
                    case 4 -> {
                        mouse.setObservations(readLine("New observations: "));
                        System.out.println("Observations changed.");
                    }
                    case 5 -> {
                        // birthDate is final: a new mouse is created with the new date
                        LocalDate newDate = LocalDate.parse(readLine("New birth date (YYYY-MM-DD): "));
                        Mouse newMouse = new Mouse(mouse.getMouseId(), newDate, mouse.getWeight(),
                                mouse.getSex(), mouse.getTemperature(), mouse.getObservations(),
                                mouse.getCr1(), mouse.getCr2());
                        mouse = replaceMouse(mouse, newMouse);
                        System.out.println("Birth date changed.");
                    }
                    case 6 -> {
                        // sex and chromosomes are final: a new mouse is created.
                        // The chromosomes are asked again so they match the new sex (XY or XX).
                        Sex newSex = readLine("New sex (M/F): ").equalsIgnoreCase("M") ? Sex.Male : Sex.Female;
                        Chromosome cr1 = new Chromosome(ChromosomeType.X,
                                readLine("Is the first X mutated? (y/n): ").equalsIgnoreCase("y"));
                        ChromosomeType secondType = (newSex == Sex.Male) ? ChromosomeType.Y : ChromosomeType.X;
                        Chromosome cr2 = new Chromosome(secondType,
                                readLine("Is the second chromosome mutated? (y/n): ").equalsIgnoreCase("y"));
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
    /**
     * Replaces a mouse of the active population with a new version of it.
     * It is used to change final attributes (birth date, sex and chromosomes),
     * which can't be changed in the existing object. The new mouse must be
     * created (and therefore validated) before calling this method, so the
     * old mouse is only deleted when the new one is valid.
     *
     * @param oldMouse mouse currently stored in the population
     * @param newMouse new version of the mouse, with the same reference code
     * @return the new mouse, so it can keep being modified
     */
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
        String id = readLine("Reference code: ").trim();
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

    /**
     * Shows a message and reads one line from the console.
     * BufferedReader.readLine() throws a checked IOException and returns null
     * at the end of the input (Ctrl+D / Ctrl+Z), so both cases are handled here
     * once, instead of in every method that reads from the keyboard.
     *
     * @param message text shown before reading
     * @return the line typed by the user, without the line break
     */
    private static String readLine(String message) {
        System.out.print(message);
        try {
            String line = input.readLine();
            if (line == null) {               // end of input: no more lines will come
                System.out.println();
                System.out.println("End of input. Goodbye!");
                System.exit(0);
            }
            return line;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static int readInt(String message) {
        while (true) {
            try {
                return Integer.parseInt(readLine(message).trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer number.");
            }
        }
    }
}