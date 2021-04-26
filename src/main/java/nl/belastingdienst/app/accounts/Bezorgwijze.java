package nl.belastingdienst.app.accounts;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Bezorgwijze {
    @Id
    @GeneratedValue
    private long id;
    private String naam;
    private String omschrijving;

    public Bezorgwijze() {
    }

    public Bezorgwijze(String naam, String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    public String getNaam() {
        return this.naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }
}
