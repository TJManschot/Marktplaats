package nl.belastingdienst.app.accounts;

import nl.belastingdienst.database.GebruikerDao;
import nl.belastingdienst.services.GebruikerValidator;
import nl.belastingdienst.services.printer.Printer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GebruikerValidatorTest {
    @Mock
    GebruikerDao gebruikerDaoMock;
    @Mock(lenient = true)
    Printer printerMock;
    @InjectMocks
    GebruikerValidator validator;
    @Mock(lenient = true)
    Adres adresMock;
    @Mock(lenient = true)
    Email emailMock;
    @Mock(lenient = true)
    Gebruikersnaam gebruikersnaamMock;
    @Mock(lenient = true)
    Bezorgwijze bezorgwijzeMock;
    @Mock(lenient = true)
    GebruikerValidator validatorMock;

    Gebruiker gebruikerMetAdres = new Gebruiker();
    Gebruiker gebruikerZonderAdres = new Gebruiker();

    @BeforeEach
    void init() {
        gebruikerMetAdres.setAdres(adresMock);
        gebruikerMetAdres.setEmail(emailMock);
        gebruikerMetAdres.setGebruikersnaam(gebruikersnaamMock);
        gebruikerMetAdres.addBezorgwijze(bezorgwijzeMock);

        gebruikerZonderAdres.setEmail(emailMock);
        gebruikerZonderAdres.setGebruikersnaam(gebruikersnaamMock);
        gebruikerZonderAdres.addBezorgwijze(bezorgwijzeMock);

        doNothing().when(printerMock).printErrorln(anyString());
    }


    @Test
    void testValideerAdres() {
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

        Adres isNull = null;

        assertTrue(validator.valideer(goedAdres1));
        assertTrue(validator.valideer(goedAdres2));
        assertTrue(validator.valideer(goedAdres3));
        assertFalse(validator.valideer(foutePostcode1));
        assertFalse(validator.valideer(foutePostcode2));
        assertFalse(validator.valideer(geenPostcode));
        assertFalse(validator.valideer(legePostcode));
        assertFalse(validator.valideer(geenStad));
        assertFalse(validator.valideer(legeStad));
        assertFalse(validator.valideer(geenStraat));
        assertFalse(validator.valideer(legeStraat));
        assertFalse(validator.valideer(geenHuisnummer));
        assertFalse(validator.valideer(leegHuisnummer));
        assertFalse(validator.valideer(isNull));
    }

    @Test
    void testValideerEmail() {
        Email correctEmail1 = new Email("thomas@manschot.nl");
        Email correctEmail2 = new Email("PiEtJe@SnOt.co.uk");
        Email geenApenstaartje = new Email("thomasmanschot.nl");
        Email geenPunt = new Email("thomas@manschotnl");
        Email puntOpHetEinde = new Email("thomas@manschot.nl.");
        Email alleenPuntOpHetEinde = new Email("thomas@manschot.");
        Email geenTekensTussenApenstaartEnPunt = new Email("thomas@.nl");
        Email geenTekensVoorApenstaart = new Email("@manschot.nl");
        Email isNull = null;

        assertTrue(validator.valideer(correctEmail1));
        assertTrue(validator.valideer(correctEmail2));
        assertFalse(validator.valideer(geenApenstaartje));
        assertFalse(validator.valideer(geenPunt));
        assertFalse(validator.valideer(puntOpHetEinde));
        assertFalse(validator.valideer(alleenPuntOpHetEinde));
        assertFalse(validator.valideer(geenTekensTussenApenstaartEnPunt));
        assertFalse(validator.valideer(geenTekensVoorApenstaart));
        assertFalse(validator.valideer(isNull));
    }

    @Test
    void testValideerGebruikersnaam() {
        Gebruikersnaam beschikbaar = new Gebruikersnaam("beschikbaar");
        Gebruikersnaam onbeschikbaar = new Gebruikersnaam("onbeschikbaar");
        Gebruikersnaam legeNaam = new Gebruikersnaam("  ");
        Gebruikersnaam isNull = null;

        when(gebruikerDaoMock.checkIfGebruikersnaamExists(beschikbaar)).thenReturn(false);
        when(gebruikerDaoMock.checkIfGebruikersnaamExists(onbeschikbaar)).thenReturn(true);

        assertTrue(validator.valideer(beschikbaar));
        assertFalse(validator.valideer(onbeschikbaar));
        assertFalse(validator.valideer(legeNaam));
        assertFalse(validator.valideer(isNull));
    }

    @Test
    void testValideerGebruikerGoedGoedGoed() {
        setupValideerGebruikerTest(true, true, true, "");

        assertTrue(validatorMock.valideer(gebruikerMetAdres));
        assertTrue(validatorMock.valideer(gebruikerZonderAdres));
    }

    @Test
    void testValideerGebruikerGoedGoedGoedOphalen() {
        setupValideerGebruikerTest(true, true, true, "ophalen");

        assertTrue(validatorMock.valideer(gebruikerMetAdres));
        assertFalse(validatorMock.valideer(gebruikerZonderAdres));
    }

    @Test
    void testValideerGebruikerGoedGoedFout() {
        setupValideerGebruikerTest(true, true, false, "");

        assertFalse(validatorMock.valideer(gebruikerMetAdres));
        assertFalse(validatorMock.valideer(gebruikerZonderAdres));
    }

    @Test
    void testValideerGebruikerGoedGoedFoutOphalen() {
        setupValideerGebruikerTest(true, true, false, "ophalen");

        assertFalse(validatorMock.valideer(gebruikerMetAdres));
        assertFalse(validatorMock.valideer(gebruikerZonderAdres));
    }

    @Test
    void testValideerGebruikerGoedFoutGoed() {
        setupValideerGebruikerTest(true, false, true, "");

        assertFalse(validatorMock.valideer(gebruikerMetAdres));
        assertFalse(validatorMock.valideer(gebruikerZonderAdres));
    }

    @Test
    void testValideerGebruikerGoedFoutGoedOphalen() {
        setupValideerGebruikerTest(true, false, true, "ophalen");

        assertFalse(validatorMock.valideer(gebruikerMetAdres));
        assertFalse(validatorMock.valideer(gebruikerZonderAdres));
    }

    @Test
    void testValideerGebruikerGoedFoutFout() {
        setupValideerGebruikerTest(true, false, false, "");

        assertFalse(validatorMock.valideer(gebruikerMetAdres));
        assertFalse(validatorMock.valideer(gebruikerZonderAdres));
    }

    @Test
    void testValideerGebruikerGoedFoutFoutOphalen() {
        setupValideerGebruikerTest(true, false, false, "ophalen");

        assertFalse(validatorMock.valideer(gebruikerMetAdres));
        assertFalse(validatorMock.valideer(gebruikerZonderAdres));
    }

    @Test
    void testValideerGebruikerFoutGoedGoed() {
        setupValideerGebruikerTest(false, true, true, "");

        assertFalse(validatorMock.valideer(gebruikerMetAdres));
        assertTrue(validatorMock.valideer(gebruikerZonderAdres));
    }

    @Test
    void testValideerGebruikerFoutGoedGoedOphalen() {
        setupValideerGebruikerTest(false, true, true, "ophalen");

        assertFalse(validatorMock.valideer(gebruikerMetAdres));
        assertFalse(validatorMock.valideer(gebruikerZonderAdres));
    }

    @Test
    void testValideerGebruikerFoutGoedFout() {
        setupValideerGebruikerTest(false, true, false, "");

        assertFalse(validatorMock.valideer(gebruikerMetAdres));
        assertFalse(validatorMock.valideer(gebruikerZonderAdres));
    }

    @Test
    void testValideerGebruikerFoutGoedFoutOphalen() {
        setupValideerGebruikerTest(false, true, false, "ophalen");

        assertFalse(validatorMock.valideer(gebruikerMetAdres));
        assertFalse(validatorMock.valideer(gebruikerZonderAdres));
    }

    @Test
    void testValideerGebruikerFoutFoutGoed() {
        setupValideerGebruikerTest(false, false, true, "");

        assertFalse(validatorMock.valideer(gebruikerMetAdres));
        assertFalse(validatorMock.valideer(gebruikerZonderAdres));
    }

    @Test
    void testValideerGebruikerFoutFoutGoedOphalen() {
        setupValideerGebruikerTest(false, false, true, "ophalen");

        assertFalse(validatorMock.valideer(gebruikerMetAdres));
        assertFalse(validatorMock.valideer(gebruikerZonderAdres));
    }

    @Test
    void testValideerGebruikerFoutFoutFout() {
        setupValideerGebruikerTest(false, false, false, "");

        assertFalse(validatorMock.valideer(gebruikerMetAdres));
        assertFalse(validatorMock.valideer(gebruikerZonderAdres));
    }

    @Test
    void testValideerGebruikerFoutFoutFoutOphalen() {
        setupValideerGebruikerTest(false, false, false, "ophalen");

        assertFalse(validatorMock.valideer(gebruikerMetAdres));
        assertFalse(validatorMock.valideer(gebruikerZonderAdres));
    }

    private void setupValideerGebruikerTest(boolean adres, boolean email, boolean naam, String bezorgwijze) {
        when(validatorMock.valideer(adresMock)).thenReturn(adres);
        when(validatorMock.valideer(emailMock)).thenReturn(email);
        when(validatorMock.valideer(gebruikersnaamMock)).thenReturn(naam);
        when(bezorgwijzeMock.getNaam()).thenReturn(bezorgwijze);
        when(validatorMock.valideer(any(Gebruiker.class))).thenCallRealMethod();
    }
}
