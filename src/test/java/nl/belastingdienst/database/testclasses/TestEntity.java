package nl.belastingdienst.database.testclasses;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TestEntity {
    @Id
    @GeneratedValue
    long id;
    String naam;

    public TestEntity() { }

    public TestEntity(String naam) {
        this.naam = naam;
    }
}
