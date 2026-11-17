package FileManager;
import LabMouses.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;


public class InputFile {

    private static final String SPLIT_REGEX = Pattern.quote(String.valueOf(OutputFile.DELIMITER));

    public static Population getPopulationFromFile(File file) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), StandardCharsets.UTF_8))) {

            String line = in.readLine();
            if (line == null) {
                throw new IllegalArgumentException("The file " + file.getName() + " is empty");
            }
            int numLine = 1;
            Population population;
            try {
                population = populationFromCSV(line);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Line " + numLine + ": " + e.getMessage());
            }

            while ((line = in.readLine()) != null) {
                numLine++;
                if (line.isBlank()) {
                    continue;
                }
                try {
                    population.addMouse(mouseFromCSV(line));
                } catch (IllegalArgumentException e) {
                    // includes NumberFormatException and repeated reference codes
                    throw new IllegalArgumentException("Line " + numLine + ": " + e.getMessage());
                }
            }
            return population;
        }
    }

    private static Population populationFromCSV(String line) {
        String[] fields = line.split(SPLIT_REGEX);
        if (fields.length != OutputFile.POPULATION_FIELDS) {
            throw new IllegalArgumentException("expected " + OutputFile.POPULATION_FIELDS
                    + " fields for the population (name;responsible;days) but found "
                    + fields.length + ": " + line);
        }
        return new Population(fields[0].trim(), fields[1].trim(), Integer.parseInt(fields[2].trim()));
    }

    private static Mouse mouseFromCSV(String line) {
        String[] f = line.split(SPLIT_REGEX, OutputFile.MOUSE_FIELDS);
        if (f.length != OutputFile.MOUSE_FIELDS) {
            throw new IllegalArgumentException("expected " + OutputFile.MOUSE_FIELDS
                    + " fields for a mouse but found " + f.length + ": " + line);
        }
        String id = f[0].trim();
        LocalDate birthDate;
        try {
            birthDate = LocalDate.parse(f[1].trim());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("wrong birth date \"" + f[1] + "\" (YYYY-MM-DD expected)");
        }
        int weight = Integer.parseInt(f[2].trim());
        Sex sex = Sex.valueOf(f[3].trim());
        float temperature = Float.parseFloat(f[4].trim());
        boolean cr1Mutated = parseBoolean(f[5]);
        boolean cr2Mutated = parseBoolean(f[6]);
        String observations = f[7];

        Chromosome cr1 = new Chromosome(ChromosomeType.X, cr1Mutated);
        Chromosome cr2 = new Chromosome(sex == Sex.Male ? ChromosomeType.Y : ChromosomeType.X, cr2Mutated);
        return new Mouse(id, birthDate, weight, sex, temperature, observations, cr1, cr2);
    }

    private static boolean parseBoolean(String text) {
        String t = text.trim();
        if (t.equalsIgnoreCase("true")) return true;
        if (t.equalsIgnoreCase("false")) return false;
        throw new IllegalArgumentException("\"" + text + "\" is not true or false");
    }
}