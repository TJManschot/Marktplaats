package nl.belastingdienst.ui;

import nl.belastingdienst.ui.accounts.Registratiemenu;
import nl.belastingdienst.ui.algemeen.Menu;
import nl.belastingdienst.ui.algemeen.Optie;

public class Hoofdmenu extends Menu {

    Optie[] opties = new Optie[]{
            new Optie("1", "Registreren", this::registreer),
            new Optie("A", "Afsluiten", () -> {})
    };

    public void start() {
        start(opties);
    }

    private void registreer() {
        Registratiemenu registratiemenu = new Registratiemenu();
        registratiemenu.start();
    }
}
