package nl.belastingdienst.database.accounts;

import nl.belastingdienst.app.accounts.Gebruiker;

public class GebruikerDao {
    private static GebruikerDao instance;

    private GebruikerDao() {

    }

    public static GebruikerDao getInstance() {
        if (instance == null)
            instance = new GebruikerDao();
        return instance;
    }

    public void save(Gebruiker gebruiker) {

    }

}
