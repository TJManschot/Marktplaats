package nl.belastingdienst.database;

import nl.belastingdienst.utility.GenericTypeGetter;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @param <E> Entity E
 * @param <K> Primary key K
 */
public abstract class Dao<E, K> {
    protected Class<E> entityClass;
    protected Class<K> keyClass;

    protected final EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public Dao(EntityManager entityManager) {
        this.entityManager = entityManager;

        Class<?>[] classes = GenericTypeGetter.INSTANCE.getGenericTypes(Dao.class, getClass());
        assert (classes != null && classes.length == 2);

        entityClass = (Class<E>) classes[0];
        keyClass = (Class<K>) classes[1];
    }

    public void save(E entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    public void saveList(List<E> list) {
        entityManager.getTransaction().begin();
        for(E entity : list) {
            entityManager.persist(entity);
        }
        entityManager.getTransaction().commit();
    }

    public E find(K primaryKey) {
        return entityManager.find(entityClass, primaryKey);
    }

    public List<E> findAll() {
        return entityManager
                .createQuery("SELECT e FROM " + entityClass.getName() + " e", entityClass)
                .getResultList();
    }
}
