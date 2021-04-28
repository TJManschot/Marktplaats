package nl.belastingdienst.database.testclasses;

import nl.belastingdienst.database.Dao;

import javax.persistence.EntityManager;

public class NonAbstractDao extends Dao<TestEntity, Long> {
    public NonAbstractDao(EntityManager entityManager) {
        super(entityManager);
    }
}
