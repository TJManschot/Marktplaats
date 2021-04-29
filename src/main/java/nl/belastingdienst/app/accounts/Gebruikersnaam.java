package nl.belastingdienst.app.accounts;

import nl.belastingdienst.MarktplaatsApp;
import nl.belastingdienst.database.GebruikerDao;
import nl.belastingdienst.utility.Valideerbaar;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class Gebruikersnaam implements Valideerbaar {
    private String gebruikersnaam;
    @Transient
    GebruikerDao gebruikerDao = GebruikerDao.getInstance(MarktplaatsApp.entityManager);

    public boolean valideer() {
        return gebruikersnaam != null
                && !gebruikersnaam.isBlank()
                && !gebruikerDao.checkIfGebruikersnaamExists(gebruikersnaam);
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }
    public String getGebruikersnaam() { return gebruikersnaam; }
}
