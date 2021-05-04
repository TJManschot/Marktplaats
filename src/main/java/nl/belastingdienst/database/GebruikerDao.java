package nl.belastingdienst.database;

import nl.belastingdienst.app.accounts.*;
import javax.persistence.EntityManager;

public class GebruikerDao extends Dao<Gebruiker, Long> {
    private static GebruikerDao instance;

    private GebruikerDao(EntityManager entityManager) { super(entityManager); }

    public static GebruikerDao getInstance(EntityManager entityManager) {
        if (instance == null)
            instance = new GebruikerDao(entityManager);
        return instance;
    }

    public boolean checkIfGebruikersnaamExists(Gebruikersnaam gebruikersnaam) {
        return checkIfFieldExists("gebruikersnaam", gebruikersnaam.getGebruikersnaam());
    }
}