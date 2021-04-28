package nl.belastingdienst.database;

import nl.belastingdienst.utility.GenericTypeGetter;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

/**
 * @param <E> Entity E
 * @param <K> Primary key K
 */
public abstract class Dao<E, K> {
    protected Class<?> entityClass;
    protected Class<?> keyClass;

    protected final EntityManager entityManager
            = Persistence.createEntityManagerFactory("MySQL-marktplaats").createEntityManager();

    public Dao() {
        Class<?>[] classes = GenericTypeGetter.INSTANCE.getGenericTypes(Dao.class, getClass());
        assert (classes != null && classes.length == 2);

        entityClass = classes[0];
        keyClass = classes[1];
    }

    public void save(E entity) {

    }

    public List<E> findAll() {
        return null;
    }

    public E find(K primaryKey) {
        return null;
    }

}
