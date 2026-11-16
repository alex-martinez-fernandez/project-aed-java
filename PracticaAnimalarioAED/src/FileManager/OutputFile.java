package FileManager;
import LabMouses.Mouse;
import LabMouses.Population;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class OutputFile {
    public static final char DELIMITER = ';';
    static final int MOUSE_FIELDS = 8;
    static final int POPULATION_FIELDS = 3;

    public static void savePopulationInFile(Population population, File file) throws IOException {
        if (population == null) {
            throw new IllegalArgumentException("There is no population to save");
        }
        // The text is checked before opening the file: if something is wrong,
        // the previous version of the file is not overwritten.
        checkNoDelimiter(population.getName(), "name of the population");
        checkNoDelimiter(population.getResponsible(), "person in charge");
        Mouse[] mice = population.getMousePopulation();
        for (Mouse m : mice) {
            checkNoDelimiter(m.getMouseId(), "reference code");
        }
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), StandardCharsets.UTF_8))) {
            out.write(populationToCSV(population));
            out.newLine();
            for (Mouse m : mice) {
                out.write(mouseToCSV(m));
                out.newLine();
            }
        }
    }
    private static String populationToCSV(Population p) {
        return p.getName() + DELIMITER + p.getResponsible() + DELIMITER + p.getDaysInLab();
    }

    private static String mouseToCSV(Mouse m) {
        return m.getMouseId() + DELIMITER
                + m.getBirthDate() + DELIMITER
                + m.getWeight() + DELIMITER
                + m.getSex().name() + DELIMITER
                + m.getTemperature() + DELIMITER
                + m.getCr1().isMutated() + DELIMITER
                + m.getCr2().isMutated() + DELIMITER
                + m.getObservations();
    }

    private static void checkNoDelimiter(String text, String field) {
        if (text.indexOf(DELIMITER) != -1) {
            throw new IllegalArgumentException("The " + field + " \"" + text
                    + "\" can't contain '" + DELIMITER + "': it is the delimiter of the file");
        }
    }



}
