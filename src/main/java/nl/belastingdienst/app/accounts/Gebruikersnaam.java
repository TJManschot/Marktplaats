package nl.belastingdienst.app.accounts;

import javax.persistence.Embeddable;

@Embeddable
public class Gebruikersnaam {

    private String gebruikersnaam;

    public Gebruikersnaam() {
    }

    public Gebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }
    public String getGebruikersnaam() { return gebruikersnaam; }
}
