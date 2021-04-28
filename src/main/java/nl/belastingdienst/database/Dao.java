package nl.belastingdienst.database;

import nl.belastingdienst.utility.GenericTypeGetter;
import nl.belastingdienst.utility.Identificeerbaar;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @param <E> Entity E
 * @param <K> Primary key K
 */
public abstract class Dao<E extends Identificeerbaar<K>, K> {
    protected Class<E> entityClass;

    protected final EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public Dao(EntityManager entityManager) {
        this.entityManager = entityManager;

        Class<?>[] classes = GenericTypeGetter.INSTANCE.getGenericTypes(Dao.class, getClass());
        assert (classes != null && classes.length == 2);
        entityClass = (Class<E>) classes[0];
    }

    public void save(E entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    public void save(List<E> list) {
        entityManager.getTransaction().begin();
        for (E entity : list) {
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

    protected List<E> findByField(String field, Object value) {
        return entityManager.createQuery(
                "SELECT e " +
                        "FROM " + entityClass.getName() + " e " +
                        "WHERE e." + field + " = '" + value + "'", entityClass)
                .getResultList();
    }

    public boolean delete(E entity) {
        if (find(entity.getKey()) == null)
            return false;
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
        return find(entity.getKey()) == null;
    }

    public int deleteAll() {
        entityManager.getTransaction().begin();
        int rowcount = entityManager.createQuery("DELETE FROM " + entityClass.getName()).executeUpdate();
        entityManager.getTransaction().commit();

        return rowcount;
    }
}
