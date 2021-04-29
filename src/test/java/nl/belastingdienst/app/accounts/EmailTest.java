package nl.belastingdienst.app.accounts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void valideer() {
        Email correctEmail1 = new Email("thomas@manschot.nl");
        Email correctEmail2 = new Email("PiEtJe@SnOt.co.uk");
        Email geenApenstaartje = new Email("thomasmanschot.nl");
        Email geenPunt = new Email("thomas@manschotnl");
        Email puntOpHetEinde = new Email("thomas@manschot.nl.");
        Email alleenPuntOpHetEinde = new Email("thomas@manschot.");
        Email geenTekensTussenApenstaartEnPunt = new Email("thomas@.nl");
        Email geenTekensVoorApenstaart = new Email("@manschot.nl");

        assertTrue(correctEmail1.valideer());
        assertTrue(correctEmail2.valideer());
        assertFalse(geenApenstaartje.valideer());
        assertFalse(geenPunt.valideer());
        assertFalse(puntOpHetEinde.valideer());
        assertFalse(alleenPuntOpHetEinde.valideer());
        assertFalse(geenTekensTussenApenstaartEnPunt.valideer());
        assertFalse(geenTekensVoorApenstaart.valideer());
    }
}