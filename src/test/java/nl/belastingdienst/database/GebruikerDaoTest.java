package nl.belastingdienst.database;

import nl.belastingdienst.app.accounts.Gebruiker;
import nl.belastingdienst.services.Services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GebruikerDaoTest {
    @Mock
    EntityManager entityManagerMock;

    @InjectMocks
    private GebruikerDao target;

    @Test
    void getInstance() {
        assertThat(target).isNotNull();
        assertThat(target).isInstanceOf(GebruikerDao.class);
        assertEquals(Gebruiker.class, target.entityClass);
    }
}