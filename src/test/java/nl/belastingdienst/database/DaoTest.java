package nl.belastingdienst.database;

import nl.belastingdienst.database.testclasses.NonAbstractDao;
import nl.belastingdienst.database.testclasses.TestEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DaoTest {
    @Mock
    public EntityManager entityManagerMock;
    @Mock
    public EntityTransaction entityTransactionMock;
    @Mock
    public TypedQuery<TestEntity> queryMock;

    @InjectMocks
    NonAbstractDao target;

    @Test
    void save() {
        TestEntity testEntity = new TestEntity("My favourite entity");

        when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);

        doNothing().when(entityTransactionMock).begin();
        doNothing().when(entityTransactionMock).commit();
        doNothing().when(entityManagerMock).persist(any());

        target.save(testEntity);

        verify(entityManagerMock, atLeastOnce()).getTransaction();
        verify(entityTransactionMock).begin();
        verify(entityTransactionMock).commit();
        verify(entityManagerMock).persist(testEntity);
    }

    @Test
    void saveList() {
        List<TestEntity> list = new ArrayList<>();
        list.add(new TestEntity("1"));
        list.add(new TestEntity("2"));

        when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
        doNothing().when(entityTransactionMock).begin();
        doNothing().when(entityTransactionMock).commit();
        doNothing().when(entityManagerMock).persist(any());

        target.saveList(list);

        verify(entityManagerMock, atLeastOnce()).getTransaction();
        verify(entityTransactionMock).begin();
        verify(entityTransactionMock).commit();
        verify(entityManagerMock, times(2)).persist(any());
    }

    @Test
    void find() {
        Long key = 0L;
        TestEntity expected = new TestEntity("My least favourite entity");

        when(entityManagerMock.find(TestEntity.class, key)).thenReturn(expected);

        TestEntity actual = target.find(key);

        assertEquals(expected, actual);
    }

    @Test
    void findAll() {
        List<TestEntity> expected = new ArrayList<>();
        expected.add(new TestEntity("My second-to-last list item"));
        expected.add(new TestEntity("My second list item"));

        when(entityManagerMock.createQuery(anyString(), eq(TestEntity.class))).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(expected);

        List<TestEntity> actual = target.findAll();

        assertEquals(expected, actual);
    }
}