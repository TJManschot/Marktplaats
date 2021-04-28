package nl.belastingdienst.database;

import nl.belastingdienst.app.accounts.Bezorgwijze;

import javax.persistence.EntityManager;

public class BezorgwijzeDao extends Dao<Bezorgwijze, Long> {
    private static BezorgwijzeDao instance;

    private BezorgwijzeDao(EntityManager entityManager) { super(entityManager); }

    public BezorgwijzeDao getInstance(EntityManager entityManager) {
        if(instance == null)
            instance = new BezorgwijzeDao(entityManager);
        return instance;
    }

}
