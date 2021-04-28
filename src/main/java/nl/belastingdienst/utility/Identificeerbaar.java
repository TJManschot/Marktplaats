package nl.belastingdienst.utility;

/**
 *
 * @param <K> Primary key
 */
public interface Identificeerbaar<K> {
    K getKey();
}