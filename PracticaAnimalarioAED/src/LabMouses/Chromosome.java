package LabMouses;

/**
 * Sex chromosome of a laboratory mouse.
 * A chromosome is immutable: it stores its type ({@link ChromosomeType#X} or
 * {@link ChromosomeType#Y}) and whether it carries a mutation. On an X chromosome
 * the mutation is associated with sterility; on a Y chromosome it is associated
 * with polygamy.
 */
public class Chromosome {
    private final ChromosomeType type;
    private final boolean mutated;


    /**
     * Creates a chromosome.
     *
     * @param type    type of the chromosome; must not be {@code null}
     * @param mutated {@code true} if the chromosome carries the mutation
     * @throws IllegalArgumentException if {@code type} is {@code null}
     */
    public Chromosome(ChromosomeType type, boolean mutated) {
        if (type == null) {
            throw new IllegalArgumentException("the chromosome type can't be null");
        }
        this.type = type;
        this.mutated = mutated;
    }

    /**
     * Returns the type of the chromosome.
     *
     * @return {@link ChromosomeType#X} or {@link ChromosomeType#Y}
     */
    public ChromosomeType getType() {
        return type;
    }

    /**
     * Tells whether the chromosome carries the mutation.
     *
     * @return {@code true} if the chromosome is mutated, {@code false} otherwise
     */
    public boolean isMutated() {
        return mutated;
    }
}