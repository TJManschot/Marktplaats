package nl.belastingdienst.app.accounts;

import nl.belastingdienst.database.GebruikerDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GebruikersnaamTest {
    @Mock
    GebruikerDao gebruikerDaoMock;

    @InjectMocks
    Gebruikersnaam target;

    @Test
    void valideerAlsGebruikersnaamAlBestaat() {
        target.setGebruikersnaam("Thomas");

        when(gebruikerDaoMock.checkIfGebruikersnaamExists("Thomas")).thenReturn(true);

        assertFalse(target.valideer());
    }

    @Test
    void valideerAlsGebruikersnaamNogNietBestaat() {
        target.setGebruikersnaam("Thomas");

        when(gebruikerDaoMock.checkIfGebruikersnaamExists("Thomas")).thenReturn(false);

        assertTrue(target.valideer());
    }

    @Test
    void valideerAlsGebruikersnaamLeegIs() {
        target.setGebruikersnaam("  ");

        assertFalse(target.valideer());
    }

    @Test
    void valideerAlsGebruikersnaamNullIs() {
        target.setGebruikersnaam(null);

        assertFalse(target.valideer());
    }
}