package nl.belastingdienst.database;

import nl.belastingdienst.database.testclasses.NonAbstractDao;
import nl.belastingdienst.database.testclasses.TestEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DaoIT {
    public EntityManager entityManager =
            Persistence.createEntityManagerFactory("H2-test-marktplaats").createEntityManager();
    Dao<TestEntity, Long> dao = new NonAbstractDao<>();

    @BeforeEach
    void init() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM TestEntity").executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Test
    void saveNewEntity() {
        TestEntity savedEntity1 = new TestEntity("Opgeslagen entiteit 1");
        TestEntity savedEntity2 = new TestEntity("Opgeslagen entiteit 2");

        dao.save(savedEntity1);
        dao.save(savedEntity2);

        entityManager.getTransaction().begin();
        List<TestEntity> result
                = entityManager
                .createQuery("SELECT e FROM TestEntity e", TestEntity.class)
                .getResultList();
        entityManager.getTransaction().commit();

        assertEquals(2, result.size());
        assertEquals(savedEntity1, result.get(0));
        assertEquals(savedEntity2, result.get(1));
    }
}