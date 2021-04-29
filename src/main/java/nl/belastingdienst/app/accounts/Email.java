package nl.belastingdienst.app.accounts;

import nl.belastingdienst.utility.Valideerbaar;

import javax.persistence.Embeddable;
import java.util.Locale;

@Embeddable
public class Email implements Valideerbaar {
    private String email;

    public boolean valideer() {
        if (email == null)
            return false;

        boolean atFound = false;
        boolean dotFound = false;
        boolean regularCharacterFound = false;


        char[] chars = email.toLowerCase(Locale.ROOT).toCharArray();

        for (char c : chars) {
            if (c == '@') {
                if (!regularCharacterFound)
                    return false;
                atFound = true;
                regularCharacterFound = false;
            }
            if (c == '.') {
                if (!regularCharacterFound)
                    return false;
                dotFound = true;
                regularCharacterFound = false;
            }
            if (Character.isLetterOrDigit(c))
                regularCharacterFound = true;
        }

        return atFound && dotFound && regularCharacterFound;
    }

    public Email() {}

    public Email(String email) { this.email = email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }
}
