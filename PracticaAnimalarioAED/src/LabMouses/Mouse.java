package LabMouses;
import java.time.LocalDate;
public class Mouse {

    public static final double MIN_TEMPERATURE = 30.0;
    public static final double MAX_TEMPERATURE = 45.0;

    private String mouseId ; //not final cause the task description says it can change
    private final LocalDate birthDate;
    private int weight;// in grams
    private Sex sex;// same as in mouseId
    private float temperature;
    private String observations;
    private Chromosome cr1;
    private Chromosome cr2;

    public Mouse (String mouseId, LocalDate birthDate, int weight, Sex sex, float temperature, String observations, Chromosome cr1, Chromosome cr2){
        validatemouseId(mouseId);
        validateBirthDate( birthDate);
        validateChromosomes(sex, cr1, cr2);
        validateWeight(weight);

        this.mouseId= mouseId;
        this.birthDate=birthDate;
        this.weight=weight;
        this.sex=sex;
        this.temperature=temperature;
        this.observations=observations;
        this.cr1=cr1;
        this.cr2=cr2;
    }

    boolean isSterile(){
        return;
    }
    boolean isPolygamous(){
        return;
    }


    void setMouseId() {
        validatemouseId(mouseId);
        this.mouseId = mouseId.trim();
    }


    public void setWeight(int weight) {
        validateWeight(weight);
        this.weight = weight;
    }

    public void setTemperature(float temperature) {
        validateTemperature(temperature);
        this.temperature = temperature;
    }

    public void setObservations(String observations) {
        this.observations = (observations == null) ? "" : observations;
    }

    private static void validatemouseId(String mouseId) {
        if (mouseId == null || mouseId.isBlank()) {
            throw new IllegalArgumentException("El código de referencia no puede estar vacío");
        }
    }

    private static void validateBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria");
        }
    }

    private static void validateWeight(int weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("El peso debe ser un entero positivo (gramos)");
        }
    }

    private static void validateTemperature(double temperature) {
        if (!Double.isFinite(temperature)
                || temperature < MIN_TEMPERATURE || temperature > MAX_TEMPERATURE) {
            throw new IllegalArgumentException(
                    "The temperature has to be between" + MIN_TEMPERATURE + " and " + MAX_TEMPERATURE + " ºC");
        }
    }

    private static void validateChromosomes(Sex sex, Chromosome firstX, Chromosome second) {
        if (sex == null) {
            throw new IllegalArgumentException("The sex id mandatory");
        }
        if (firstX == null || second == null) {
            throw new IllegalArgumentException("chromosomes missing");
        }
        if (firstX.getType() != ChromosomeType.X) {
            throw new IllegalArgumentException("The first chromosome has to be X");
        }
    }

}
