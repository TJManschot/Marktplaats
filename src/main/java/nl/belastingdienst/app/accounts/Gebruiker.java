package nl.belastingdienst.app.accounts;

import nl.belastingdienst.utility.Identificeerbaar;
import nl.belastingdienst.utility.Valideerbaar;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

@Entity
public class Gebruiker implements Valideerbaar, Identificeerbaar<Long> {
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
    private final Set<Bezorgwijze> bezorgwijzen = new LinkedHashSet<>();

    @Override
    public boolean valideer() {
        boolean adresVerplicht = false;

        for (Bezorgwijze bezorgwijze : bezorgwijzen) {
            if (bezorgwijze.getNaam().toLowerCase(Locale.ROOT).equals("ophalen")) {
                adresVerplicht = true;
                break;
            }
        }

        if (gebruikersnaam == null || email == null || (adresVerplicht && adres == null))
            return false;

        return gebruikersnaam.valideer() && email.valideer() && (adres == null || adres.valideer());
    }

    @Override
    public Long getKey() {
        return lidnummer;
    }

    public void setGebruikersnaam(Gebruikersnaam gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public Set<Bezorgwijze> getBezorgwijzen() {
        return bezorgwijzen;
    }

    public void addBezorgwijze(Bezorgwijze bezorgwijze) {
        bezorgwijzen.add(bezorgwijze);
    }

    public void removeBezorgwijze(Bezorgwijze bezorgwijze) {
        bezorgwijzen.remove(bezorgwijze);
    }
}
