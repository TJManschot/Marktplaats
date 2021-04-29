package nl.belastingdienst.app.accounts;

import nl.belastingdienst.utility.Valideerbaar;

import javax.persistence.Embeddable;
import java.util.Locale;

@Embeddable
public class Email implements Valideerbaar {
    private String email;

    public boolean valideer() {
        boolean atFound = false;
        boolean dotFound = false;
        boolean regularCharacterFound = false;


        char[] chars = email.toLowerCase(Locale.ROOT).toCharArray();
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '@') {
                if (!regularCharacterFound)
                    return false;
                atFound = true;
                regularCharacterFound = false;
            }
            if (chars[i] == '.') {
                if (!regularCharacterFound)
                    return false;
                dotFound = true;
                regularCharacterFound = false;
            }
            if (Character.isLetterOrDigit(chars[i]))
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
