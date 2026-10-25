package LabMouses;
import java.time.LocalDate;

/**
 * Laboratory mouse with its biological data and its pair of sex chromosomes.
 * Sterility and polygamy are not stored as attributes: they are derived from
 * the state of the chromosomes, so they can never contradict it.
 *   A male has one X chromosome and one Y chromosome. He is sterile if his X
 *       is mutated and polygamous if his Y is mutated.
 *   A female has two X chromosomes. She is sterile only if both X are mutated,
 *       and she is never polygamous.
 *
 */
public class Mouse {

    public static final double MIN_TEMPERATURE = 30.0;
    public static final double MAX_TEMPERATURE = 45.0;

    private String mouseId ; //not final cause the task description says it can change
    private final LocalDate birthDate; //En formato AAAA-MM-DD
    private int weight;// in grams
    private Sex sex;// same as in mouseId
    private float temperature;
    private String observations;
    private Chromosome cr1;
    private Chromosome cr2;

    /**
     * Creates a mouse after validating its reference code, date of birth,
     * chromosomes and weight.
     *
     * @param mouseId      reference code; must not be {@code null} or blank
     * @param birthDate    date of birth; must not be {@code null}
     * @param weight       weight in grams; must be positive
     * @param sex          sex of the mouse; must not be {@code null}
     * @param temperature  body temperature, in ºC
     * @param observations free-text observations
     * @param cr1          first sex chromosome; must be an X chromosome
     * @param cr2          second sex chromosome
     * @throws IllegalArgumentException if any of the validated values is not valid
     */
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

    /**
     * Tells whether the mouse is sterile, based on its X chromosomes.
     *  A male is sterile if his only X chromosome is mutated.
     *  A female is sterile only if both of her X chromosomes are mutated.</li>
     *
     * @return {@code true} if the mouse is sterile, {@code false} otherwise
     */
    boolean isSterile(){
        if(Sex sex == Sex.Male && Chromosome cr1.isMutated()){
            return true;//What happends if Cr1 is not X but Y --> need to make sure
        }if(Sex sex == Sex.Female && Chromosome cr1.isMutated() && Chromosome cr2.isMutated()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Tells whether the mouse is polygamous. Only a male whose Y chromosome
     * is mutated can be polygamous.
     *
     * @return {@code true} if the mouse is polygamous, {@code false} otherwise
     */
    boolean isPolygamous(){
        if()
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

    /**
     * Checks that a reference code is not {@code null} or blank.
     *
     * @param mouseId reference code to check
     * @throws IllegalArgumentException if the code is {@code null} or blank
     */
    private static void validatemouseId(String mouseId) {
        if (mouseId == null || mouseId.isBlank()) {
            throw new IllegalArgumentException("El código de referencia no puede estar vacío");
        }
    }

    /**
     * Checks that a date of birth is not {@code null}.
     *
     * @param birthDate date of birth to check
     * @throws IllegalArgumentException if the date is {@code null}
     */
    private static void validateBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria");
        }
    }

    /**
     * Checks that a weight is a positive integer.
     *
     * @param weight weight in grams
     * @throws IllegalArgumentException if the weight is zero or negative
     */
    private static void validateWeight(int weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("El peso debe ser un entero positivo (gramos)");
        }
    }

    /**
     * Checks that a temperature is finite and between {@link #MIN_TEMPERATURE}
     * and {@link #MAX_TEMPERATURE}.
     *
     * @param temperature temperature in ºC
     * @throws IllegalArgumentException if the temperature is not finite or is out of range
     */
    private static void validateTemperature(double temperature) {
        if (!Double.isFinite(temperature)
                || temperature < MIN_TEMPERATURE || temperature > MAX_TEMPERATURE) {
            throw new IllegalArgumentException(
                    "The temperature has to be between" + MIN_TEMPERATURE + " and " + MAX_TEMPERATURE + " ºC");
        }
    }

    /**
     * Checks the sex and the chromosomes of a mouse: the sex and both chromosomes
     * must not be {@code null}, and the first chromosome must be an X chromosome.
     *
     * @param sex    sex of the mouse
     * @param firstX first chromosome
     * @param second second chromosome
     * @throws IllegalArgumentException if the sex or a chromosome is missing,
     *                                  or if the first chromosome is not X
     */
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