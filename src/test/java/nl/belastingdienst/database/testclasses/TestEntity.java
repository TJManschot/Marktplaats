package nl.belastingdienst.database.testclasses;

import nl.belastingdienst.utility.Identificeerbaar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TestEntity implements Identificeerbaar<Long> {
    @Id
    @GeneratedValue
    private long id;
    private String naam;

    public TestEntity() { }

    public TestEntity(String naam) {
        this.naam = naam;
    }

    @Override
    public Long getKey() {
        return id;
    }

    public String getNaam() {
        return naam;
    }
}
