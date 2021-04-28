package nl.belastingdienst.database;

import nl.belastingdienst.app.accounts.Gebruiker;

public class GebruikerDao extends Dao<Gebruiker, Long> {
    private static GebruikerDao instance;

    private GebruikerDao() { }

    public static GebruikerDao getInstance() {
        if (instance == null)
            instance = new GebruikerDao();
        return instance;
    }
}