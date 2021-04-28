package nl.belastingdienst.app.accounts;

import nl.belastingdienst.utility.Identificeerbaar;

import javax.persistence.*;

@Entity
public class Bezorgwijze implements Identificeerbaar<Long> {
    @Id
    @GeneratedValue
    private long id;
    private String naam;
    private String omschrijving;

    @Override
    public Long getKey() {
        return id;
    }

    public Bezorgwijze() {
    }

    public Bezorgwijze(String naam, String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Bezorgwijze))
            return false;
        Bezorgwijze other = (Bezorgwijze) obj;
        return other.getNaam().equals(naam) && other.getOmschrijving().equals(omschrijving);
    }

    public String getNaam() {
        return this.naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }
}
