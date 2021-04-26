package nl.belastingdienst.app.accounts;

import nl.belastingdienst.utility.Valideerbaar;

import javax.persistence.Embeddable;

@Embeddable
public class Gebruikersnaam implements Valideerbaar {
    String gebruikersnaam;

    public boolean valideer() { return true; }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }
    public String getGebruikersnaam() { return gebruikersnaam; }
}
