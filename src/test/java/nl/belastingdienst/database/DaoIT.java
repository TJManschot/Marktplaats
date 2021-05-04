package nl.belastingdienst.database;

import nl.belastingdienst.database.testclasses.NonAbstractDao;
import nl.belastingdienst.database.testclasses.TestEntity;
import nl.belastingdienst.services.Services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DaoIT {
    private final EntityManager entityManager =
            Persistence.createEntityManagerFactory("H2-test-marktplaats").createEntityManager();
    NonAbstractDao dao = new NonAbstractDao(entityManager);

    @BeforeEach
    void init() {
        Services.INSTANCE.entityManager(entityManager);

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
        TestEntity foundByField = dao.findByField("naam", "Opgeslagen entiteit 2").get(0);

        assertEquals(2, result.size());
        assertEquals(savedEntity1, result.get(0));
        assertEquals(savedEntity2, result.get(1));
        assertEquals(savedEntity1, singleResult);
        assertEquals(savedEntity2, foundByField);
        assertNull(dao.find(10L));
    }

    @Test
    void saveListAndDeleteSomeAndDeleteAll() {
        List<TestEntity> list = Arrays.asList(
                new TestEntity("Alfred"),
                new TestEntity("Henk"),
                new TestEntity("Dolf"),
                new TestEntity("Ollie")
        );

        dao.save(list);
        List<TestEntity> currentEntities = dao.findAll();
        assertEquals(4, currentEntities.size());
        assertEquals(list, currentEntities);

        assertTrue(dao.delete(dao.findByField("naam", "Dolf").get(0)));
        assertFalse(dao.delete(new TestEntity("Rocodil")));

        currentEntities = dao.findAll();
        assertEquals(3, currentEntities.size());
        assertEquals("Alfred", currentEntities.get(0).getNaam());
        assertEquals("Henk", currentEntities.get(1).getNaam());
        assertEquals("Ollie", currentEntities.get(2).getNaam());

        assertEquals(3, dao.deleteAll());
        assertTrue(dao.findAll().isEmpty());
    }
}