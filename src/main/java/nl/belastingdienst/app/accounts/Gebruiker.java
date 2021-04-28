package nl.belastingdienst.app.accounts;

import nl.belastingdienst.utility.Identificeerbaar;
import nl.belastingdienst.utility.Valideerbaar;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Gebruiker implements Valideerbaar, Identificeerbaar<Long> {
    @Id
    @GeneratedValue
    private long lidnummer;
    private LocalDate registratiedatum;
    @Embedded
    private Gebruikersnaam gebruikersnaam;
    @Embedded
    private Wachtwoord wachtwoord;
    @Embedded
    private Email email;
    @Embedded
    private Adres adres;
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "bezorgwijzeregistratie",
            joinColumns = @JoinColumn(name = "gebruiker"),
            inverseJoinColumns = @JoinColumn(name = "bezorgwijze"))
    private final Set<Bezorgwijze> bezorgwijzen = new LinkedHashSet<>();

    @Override
    public boolean valideer() {
        return true;
    }

    @Override
    public Long getKey() {
        return lidnummer;
    }

    public void setGebruikersnaam(Gebruikersnaam gebruikersnaam) { this.gebruikersnaam = gebruikersnaam; }
    public void setEmail(Email email) { this.email = email; }
    public void setAdres(Adres adres) { this.adres = adres; }
    public void addBezorgwijze(Bezorgwijze bezorgwijze) {
        bezorgwijzen.add(bezorgwijze);
    }
    public void removeBezorgwijze(Bezorgwijze bezorgwijze) {
        bezorgwijzen.remove(bezorgwijze);
    }
}
