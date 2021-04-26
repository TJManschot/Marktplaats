package nl.belastingdienst.app.accounts;

import nl.belastingdienst.utility.Valideerbaar;

import javax.persistence.Embeddable;

@Embeddable
public class Email implements Valideerbaar {
    String email;

    public boolean valideer() { return true; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }
}
