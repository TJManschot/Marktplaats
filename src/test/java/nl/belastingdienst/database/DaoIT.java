package nl.belastingdienst.database;

import nl.belastingdienst.database.testclasses.TestEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DaoIT {
    @Mock
    public EntityManager entityManagerMock =
            Persistence.createEntityManagerFactory("H2-test-marktplaats").createEntityManager();

    @InjectMocks @SuppressWarnings("unchecked")
    Dao<TestEntity, Long> spy = mock(Dao.class, withSettings().defaultAnswer(CALLS_REAL_METHODS));

    @BeforeEach
    void init() {
        entityManagerMock.getTransaction().begin();
        entityManagerMock.createQuery("DELETE FROM TestEntity").executeUpdate();
        entityManagerMock.getTransaction().commit();
    }

    @Test
    void saveAndFindNewEntities() {
        TestEntity savedEntity1 = new TestEntity("Opgeslagen entiteit 1");
        TestEntity savedEntity2 = new TestEntity("Opgeslagen entiteit 2");

        spy.save(savedEntity1);
        spy.save(savedEntity2);

        List<TestEntity> result = spy.findAll();
        TestEntity singleResult = spy.find(0L);

        assertEquals(2, result.size());
        assertEquals(savedEntity1, result.get(0));
        assertEquals(savedEntity2, result.get(1));
        assertEquals(savedEntity1, singleResult);
    }
}