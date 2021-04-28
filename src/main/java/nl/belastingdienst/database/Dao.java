package nl.belastingdienst.database;

import nl.belastingdienst.utility.GenericTypeGetter;

/**
 *
 * @param <E> Entity E
 * @param <K> Primary key K
 */
public abstract class Dao<E, K> {
    protected Class<?> entityClass;
    protected Class<?> keyClass;

    public Dao() {
        Class<?>[] classes = GenericTypeGetter.INSTANCE.getGenericTypes(Dao.class, getClass());
        assert(classes != null && classes.length == 2);

        entityClass = classes[0];
        keyClass = classes[1];
    }

    public void save(E entity) {

    }
}
