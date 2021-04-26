package nl.belastingdienst.app.accounts;

import nl.belastingdienst.utility.Valideerbaar;

import javax.persistence.Embeddable;

@Embeddable
public class Wachtwoord implements Valideerbaar {
    private String wachtwoord;

    public boolean valideer() { return true; }
}
