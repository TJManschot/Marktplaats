package nl.belastingdienst.app.accounts;

import javax.persistence.Embeddable;

@Embeddable
public class Email {
    private String email;

    public Email() {}

    public Email(String email) { this.email = email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }
}
