package nl.belastingdienst.database;

import nl.belastingdienst.database.testclasses.NonAbstractDao;
import nl.belastingdienst.database.testclasses.TestEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DaoIT {
    private final EntityManager entityManager =
            Persistence.createEntityManagerFactory("H2-test-marktplaats").createEntityManager();
    NonAbstractDao dao = new NonAbstractDao(entityManager);

    @BeforeEach
    void init() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM TestEntity").executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Test
    void saveAndFindNewEntities() {
        TestEntity savedEntity1 = new TestEntity("Opgeslagen entiteit 1");
        TestEntity savedEntity2 = new TestEntity("Opgeslagen entiteit 2");

        dao.save(savedEntity1);
        dao.save(savedEntity2);

        List<TestEntity> result = dao.findAll();
        TestEntity singleResult = dao.find(1L);

        assertEquals(2, result.size());
        assertEquals(savedEntity1, result.get(0));
        assertEquals(savedEntity2, result.get(1));
        assertEquals(savedEntity1, singleResult);
    }
}