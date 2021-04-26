package nl.belastingdienst.utility;

public interface Valideerbaar {
    boolean valideer() throws IllegalArgumentException;
}
