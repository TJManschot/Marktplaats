package nl.belastingdienst.app.accounts;

import nl.belastingdienst.utility.Valideerbaar;

import javax.persistence.Embeddable;

@Embeddable
public class Adres implements Valideerbaar {
    private String postcode;
    private String stad;
    private String straat;
    private String huisnummer;

    public boolean valideer() {
        if (postcode == null || stad == null || straat == null || huisnummer == null)
            return false;
        if (postcode.isBlank() || stad.isBlank() || straat.isBlank() || huisnummer.isBlank())
            return false;

        char[] postcodeArray = postcode.toCharArray();
        int aantalCijfers = 0;
        int aantalLetters = 0;

        for (char c : postcodeArray) {
            if (c == ' ')
                continue;
            if (aantalCijfers < 4) {
                if (!Character.isDigit(c))
                    return false;
                aantalCijfers++;
                continue;
            }
            if (aantalLetters < 2) {
                if (!Character.isLetter(c))
                    return false;
                aantalLetters++;
                continue;
            }
            return false;
        }

        return aantalCijfers == 4 && aantalLetters == 2;
    }

    public Adres() {}

    public Adres(String postcode, String stad, String straat, String huisnummer) {
        this.postcode = postcode;
        this.stad = stad;
        this.straat = straat;
        this.huisnummer = huisnummer;
    }

    public static class Builder {
        private String postcode;
        private String stad;
        private String straat;
        private String huisnummer;

        public Builder postcode(String postcode) { this.postcode = postcode; return this; }
        public Builder stad(String stad) { this.stad = stad; return this; }
        public Builder straat(String straat) { this.straat = straat; return this; }
        public Builder huisnummer(String huisnummer) { this.huisnummer = huisnummer; return this; }

        public Adres build() {
            return new Adres(postcode, stad, straat, huisnummer);
        }
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStad() {
        return stad;
    }

    public void setStad(String stad) {
        this.stad = stad;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }
}
