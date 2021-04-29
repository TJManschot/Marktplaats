package nl.belastingdienst.ui;

import nl.belastingdienst.ui.accounts.Registratiemenu;
import nl.belastingdienst.ui.algemeen.Menu;
import nl.belastingdienst.ui.algemeen.Optie;

public class Hoofdmenu extends Menu {
    public void start() {
        opties = new Optie[]{
                new Optie("1", "Registreren", new Registratiemenu()::start),
                new Optie("A", "Afsluiten", () -> { })
        };

        draaiMenu();
    }
}
