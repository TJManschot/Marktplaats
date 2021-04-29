package nl.belastingdienst.app.accounts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GebruikerTest {
    Gebruiker target = new Gebruiker();

    Gebruikersnaam validGebruikersnaam = new Gebruikersnaam();
    Email validEmail = new Email();
    Adres validAdres = new Adres();

    Gebruikersnaam invalidGebruikersnaam = new Gebruikersnaam();
    Email invalidEmail = new Email();
    Adres invalidAdres = new Adres();

    Bezorgwijze andersDanOphalen = new Bezorgwijze("magazijn", "");
    Bezorgwijze ophalen = new Bezorgwijze("ophalen", "");

    @BeforeEach
    public void init() {
        validGebruikersnaam.setGebruikersnaam("Thomasi32nrkjfwn32hndlkw");
        validEmail.setEmail("example@example.com");
        validAdres.setPostcode("1234 AB");
        validAdres.setStad("Apeldoorn");
        validAdres.setStraat("Asselsestraat");
        validAdres.setHuisnummer("1A");

        invalidGebruikersnaam.setGebruikersnaam(null);
        invalidEmail.setEmail(null);
        invalidAdres.setPostcode("1234 AB1");
        invalidAdres.setStad("Apeldoorn");
        invalidAdres.setStraat("Asselsestraat");
        invalidAdres.setHuisnummer("1A");
    }

    @Test
    void valideerAllesValid() {
        target.setGebruikersnaam(validGebruikersnaam);
        target.setEmail(validEmail);
        target.setAdres(validAdres);
        target.addBezorgwijze(andersDanOphalen);

        assertTrue(target.valideer());
    }

    @Test
    void valideerGeenAdresNietOphalen() {
        target.setGebruikersnaam(validGebruikersnaam);
        target.setEmail(validEmail);
        target.addBezorgwijze(andersDanOphalen);

        assertTrue(target.valideer());
    }

    @Test
    void valideerGeenAdresWelOphalen() {
        target.setGebruikersnaam(validGebruikersnaam);
        target.setEmail(validEmail);
        target.addBezorgwijze(ophalen);

        assertFalse(target.valideer());
    }

    @Test
    void valideerInvalideGebruikersnaam() {
        target.setGebruikersnaam(invalidGebruikersnaam);
        target.setEmail(validEmail);

        assertFalse(target.valideer());
    }

    @Test
    void valideerInvalideEmail() {
        target.setGebruikersnaam(validGebruikersnaam);
        target.setEmail(invalidEmail);
        target.addBezorgwijze(ophalen);

        assertFalse(target.valideer());
    }

    @Test
    void valideerInvalideAdres() {
        target.setGebruikersnaam(validGebruikersnaam);
        target.setEmail(validEmail);
        target.setAdres(invalidAdres);
        target.addBezorgwijze(andersDanOphalen);

        assertFalse(target.valideer());
    }
}