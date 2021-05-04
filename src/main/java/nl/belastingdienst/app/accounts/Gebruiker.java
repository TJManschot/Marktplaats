package nl.belastingdienst.app.accounts;

import nl.belastingdienst.utility.Identificeerbaar;

import javax.persistence.*;
import java.util.*;

@Entity
public class Gebruiker implements Identificeerbaar<Long> {
    @Id
    @GeneratedValue
    private long lidnummer;
    @Embedded
    private Gebruikersnaam gebruikersnaam;
    @Embedded
    private Email email;
    @Embedded
    private Adres adres;
    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "bezorgwijzeregistratie",
            joinColumns = @JoinColumn(name = "gebruiker"),
            inverseJoinColumns = @JoinColumn(name = "bezorgwijze"))
    private final List<Bezorgwijze> bezorgwijzen = new ArrayList<>();

    @Override
    public Long getKey() {
        return lidnummer;
    }

    public Gebruikersnaam getGebruikersnaam() { return gebruikersnaam; }

    public void setGebruikersnaam(Gebruikersnaam gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public Email getEmail() { return email; }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public List<Bezorgwijze> getBezorgwijzen() {
        return bezorgwijzen;
    }

    public void addBezorgwijze(Bezorgwijze bezorgwijze) {
        bezorgwijzen.add(bezorgwijze);
    }

    public void removeBezorgwijze(Bezorgwijze bezorgwijze) {
        bezorgwijzen.remove(bezorgwijze);
    }
}
