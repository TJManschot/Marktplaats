package nl.belastingdienst.app.accounts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdresTest {

    @Test
    void valideer() {
        Adres goedAdres1 = new Adres.Builder()
                .postcode("1234LR")
                .stad("Lübeck")
                .straat("DSKj")
                .huisnummer("28F-104")
                .build();

        Adres goedAdres2 = new Adres.Builder()
                .postcode("1234 LR")
                .stad("Lübeck")
                .straat("DSKj")
                .huisnummer("28F-104")
                .build();

        Adres goedAdres3 = new Adres.Builder()
                .postcode("   1 23 4L  R ")
                .stad("Lübeck")
                .straat("DSKj")
                .huisnummer("28F-104")
                .build();

        Adres foutePostcode1 = new Adres.Builder()
                .postcode("123KE2")
                .stad("Lübeck")
                .straat("DSKj")
                .huisnummer("28F-104")
                .build();

        Adres foutePostcode2 = new Adres.Builder()
                .postcode("1234 LRR")
                .stad("Lübeck")
                .straat("DSKj")
                .huisnummer("28F-104")
                .build();

        Adres geenPostcode = new Adres.Builder()
                .stad(" ")
                .straat("DSKj")
                .huisnummer("28F-104")
                .build();

        Adres legePostcode = new Adres.Builder()
                .postcode("  ")
                .stad("Lübeck")
                .straat("DSKj")
                .huisnummer("28F-104")
                .build();

        Adres geenStad = new Adres.Builder()
                .postcode("1234 LR")
                .straat("DSKj")
                .huisnummer("28F-104")
                .build();

        Adres legeStad = new Adres.Builder()
                .postcode("1234 LR")
                .stad(" ")
                .straat("DSKj")
                .huisnummer("28F-104")
                .build();

        Adres geenStraat = new Adres.Builder()
                .postcode("1234 LR")
                .stad("Sleeswijk")
                .huisnummer("28F-104")
                .build();

        Adres legeStraat = new Adres.Builder()
                .postcode("1234 LR")
                .stad("Sleeswijk")
                .straat("  ")
                .huisnummer("28F-104")
                .build();

        Adres geenHuisnummer = new Adres.Builder()
                .postcode("1234 LR")
                .stad("Sleeswijk")
                .straat("DSKj")
                .build();

        Adres leegHuisnummer = new Adres.Builder()
                .postcode("1234 LR")
                .stad("Sleeswijk")
                .straat("DSKj")
                .huisnummer("  ")
                .build();

        assertTrue(goedAdres1.valideer());
        assertTrue(goedAdres2.valideer());
        assertTrue(goedAdres3.valideer());
        assertFalse(foutePostcode1.valideer());
        assertFalse(foutePostcode2.valideer());
        assertFalse(geenPostcode.valideer());
        assertFalse(legePostcode.valideer());
        assertFalse(geenStad.valideer());
        assertFalse(legeStad.valideer());
        assertFalse(geenStraat.valideer());
        assertFalse(legeStraat.valideer());
        assertFalse(geenHuisnummer.valideer());
        assertFalse(leegHuisnummer.valideer());
    }
}