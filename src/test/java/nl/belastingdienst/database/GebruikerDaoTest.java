package nl.belastingdienst.database;

import nl.belastingdienst.app.accounts.Gebruiker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GebruikerDaoTest {
    private final GebruikerDao target = GebruikerDao.getInstance();

    @Test
    void getInstance() {
        assertThat(target).isNotNull();
        assertThat(target).isInstanceOf(GebruikerDao.class);
        assertEquals(Gebruiker.class, target.entityClass);
        assertEquals(Long.class, target.keyClass);
    }
}