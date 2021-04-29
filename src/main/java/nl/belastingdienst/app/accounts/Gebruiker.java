package nl.belastingdienst.app.accounts;

import nl.belastingdienst.utility.Identificeerbaar;
import nl.belastingdienst.utility.Valideerbaar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

@Entity
public class Gebruiker implements Valideerbaar, Identificeerbaar<Long> {
    @Transient
    private final Logger log = LoggerFactory.getLogger(Gebruiker.class);

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
        boolean resultaat = true;

        for (Bezorgwijze bezorgwijze : bezorgwijzen) {
            if (bezorgwijze.getNaam().toLowerCase(Locale.ROOT).equals("ophalen")) {
                adresVerplicht = true;
                break;
            }
        }

        if (gebruikersnaam == null) {
            log.warn("Gebruikersnaam is verplicht.");
            resultaat = false;
        }

        if (email == null) {
            log.warn("Email-adres is verplicht.");
            resultaat = false;
        }

        if (adresVerplicht && adres == null) {
            log.warn("Adres is verplicht als u de bezorgoptie Ophalen wil ondersteunen.");
            resultaat = false;
        }

        return resultaat && gebruikersnaam.valideer() && email.valideer() && (adres == null || adres.valideer());
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
