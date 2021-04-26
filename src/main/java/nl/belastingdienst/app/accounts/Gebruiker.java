package nl.belastingdienst.app.accounts;

import nl.belastingdienst.utility.Valideerbaar;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Gebruiker implements Valideerbaar {
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
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "bezorgwijzeregistratie",
            joinColumns = @JoinColumn(name = "gebruiker"),
            inverseJoinColumns = @JoinColumn(name = "bezorgwijze"))
    private final Set<Bezorgwijze> bezorgwijzen = new LinkedHashSet<>();

    @Override
    public boolean valideer() {
        return true;
    }

    public void setGebruikersnaam(Gebruikersnaam gebruikersnaam) { this.gebruikersnaam = gebruikersnaam; }
    public void setEmail(Email email) { this.email = email; }
    public void setAdres(Adres adres) { this.adres = adres; }
    public void addBezorgwijze(Bezorgwijze b) {
        bezorgwijzen.add(b);
    }
}
