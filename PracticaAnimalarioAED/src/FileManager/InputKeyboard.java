package FileManager;
import LabMouses.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputKeyboard {

    private static final Scanner input = new Scanner(System.in);

    public static String readLine(String message) {
        System.out.print(message);
        if (!input.hasNextLine()) {
            System.out.println();
            System.out.println("End of input. Goodbye!");
            System.exit(0);
        }
        return input.nextLine();
    }

    public static String readNonBlank(String message) {
        while (true) {
            String line = readLine(message).trim();
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("This field can't be empty.");
        }
    }


    public static int readInt(String message) {
        while (true) {
            try {
                return Integer.parseInt(readLine(message).trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer number.");
            }
        }
    }

    public static float readFloat(String message) {
        while (true) {
            try {
                return Float.parseFloat(readLine(message).trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number (e.g. 37.5).");
            }
        }
    }

    public static LocalDate readDate(String message) {
        while (true) {
            try {
                return LocalDate.parse(readLine(message).trim());
            } catch (DateTimeParseException e) {
                System.out.println("Please enter a valid date with format YYYY-MM-DD.");
            }
        }
    }

    public static boolean readYesNo(String message) {
        while (true) {
            String answer = readLine(message).trim();
            if (answer.equalsIgnoreCase("y")) return true;
            if (answer.equalsIgnoreCase("n")) return false;
            System.out.println("Please answer y or n.");
        }
    }

    public static Sex readSex(String message) {
        while (true) {
            String answer = readLine(message).trim();
            if (answer.equalsIgnoreCase("M")) return Sex.Male;
            if (answer.equalsIgnoreCase("F")) return Sex.Female;
            System.out.println("Please answer M or F.");
        }
    }

    public static Chromosome getChromosome(ChromosomeType type, String description) {
        boolean mutated = readYesNo("Is the " + description + " chromosome mutated? (y/n): ");
        return new Chromosome(type, mutated);
    }

    public static Chromosome[] getChromosomes(Sex sex) {
        Chromosome cr1 = getChromosome(ChromosomeType.X, "first X");
        Chromosome cr2 = (sex == Sex.Male)
                ? getChromosome(ChromosomeType.Y, "Y")
                : getChromosome(ChromosomeType.X, "second X");
        return new Chromosome[]{cr1, cr2};
    }

    public static Mouse getMouseFromKeyboard() {
        String id = readNonBlank("Reference code: ");
        LocalDate birthDate = readDate("Birth date (YYYY-MM-DD): ");
        int weight = readInt("Weight (g): ");
        float temperature = readFloat("Temperature (ºC, between " + Mouse.MIN_TEMPERATURE
                + " and " + Mouse.MAX_TEMPERATURE + "): ");
        String observations = readLine("Observations: ");
        Sex sex = readSex("Sex (M/F): ");
        Chromosome[] cr = getChromosomes(sex);
        return new Mouse(id, birthDate, weight, sex, temperature, observations, cr[0], cr[1]);
    }

    public static Population getPopulationFromKeyboard() {
        String name = readNonBlank("Name: ");
        String responsible = readNonBlank("Person in charge: ");
        int days = readInt("Days in the lab (" + Population.MIN_DAYS + "-" + Population.MAX_DAYS + "): ");
        return new Population(name, responsible, days);
    }
}