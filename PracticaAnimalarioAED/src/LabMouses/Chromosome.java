package LabMouses;

public class Chromosome {

    private final ChromosomeType type;
    private final boolean mutated;


    public Chromosome(ChromosomeType type, boolean mutated) {
        if (type == null) {
            throw new IllegalArgumentException("the chromosome type can't be null");
        }
        this.type = type;
        this.mutated = mutated;
    }

    public ChromosomeType getType() {
        return type;
    }

    public boolean isMutated() {
        return mutated;
    }
