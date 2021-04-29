package nl.belastingdienst.app.accounts;

import nl.belastingdienst.utility.Valideerbaar;

import javax.persistence.Embeddable;

@Embeddable
public class Adres implements Valideerbaar {
    private String postcode;
    private String stad;
    private String straat;
    private String huisnummer;

    public boolean valideer() { return true; }

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
