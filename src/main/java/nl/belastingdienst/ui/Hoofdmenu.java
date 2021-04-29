package nl.belastingdienst.ui;

import nl.belastingdienst.ui.accounts.Registratiemenu;
import nl.belastingdienst.ui.algemeen.Menu;
import nl.belastingdienst.ui.algemeen.Optie;

public class Hoofdmenu extends Menu {
    Registratiemenu registratiemenu;
    
    public Hoofdmenu() {
        opties = new Optie[]{
                new Optie("1", "Registreren", () -> {
                    registratiemenu = new Registratiemenu();
                    registratiemenu.start();
                }),
                new Optie("A", "Afsluiten", () -> { })
        };
    }

    public void start() {
        draaiMenu();
    }
}
