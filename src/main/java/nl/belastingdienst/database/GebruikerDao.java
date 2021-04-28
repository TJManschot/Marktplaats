package nl.belastingdienst.database;

import nl.belastingdienst.app.accounts.Gebruiker;

public class GebruikerDao<E extends Gebruiker> extends Dao<E, Long> {
    private static GebruikerDao<Gebruiker> instance;

    private GebruikerDao() { }

    public static GebruikerDao<Gebruiker> getInstance() {
        if (instance == null)
            instance = new GebruikerDao<>();
        return instance;
    }
}