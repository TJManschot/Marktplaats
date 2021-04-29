package nl.belastingdienst.app.accounts;

import nl.belastingdienst.MarktplaatsApp;
import nl.belastingdienst.database.GebruikerDao;
import nl.belastingdienst.utility.Valideerbaar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class Gebruikersnaam implements Valideerbaar {
    @Transient
    private final Logger log = LoggerFactory.getLogger(Gebruikersnaam.class);

    private String gebruikersnaam;
    @Transient
    GebruikerDao gebruikerDao = GebruikerDao.getInstance(MarktplaatsApp.entityManager);

    public boolean valideer() {
        boolean resultaat = true;

        if (gebruikersnaam == null || gebruikersnaam.isBlank()) {
            log.warn("De gebruikersnaam mag niet leeg zijn.");
            resultaat = false;
        }

        if (gebruikerDao.checkIfGebruikersnaamExists(gebruikersnaam)) {
            log.warn("De door u gekozen gebruikersnaam is reeds in gebruik.");
            resultaat = false;
        }

        return resultaat;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }
    public String getGebruikersnaam() { return gebruikersnaam; }
}
