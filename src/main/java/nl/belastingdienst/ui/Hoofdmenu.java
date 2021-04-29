package nl.belastingdienst.ui;

import nl.belastingdienst.ui.accounts.Registratiemenu;
import nl.belastingdienst.ui.algemeen.Menu;
import nl.belastingdienst.ui.algemeen.Optie;

public enum Hoofdmenu implements Menu {
    INSTANCE;

    Optie[] opties = new Optie[]{
            new Optie("1", "Registreren", Registratiemenu.INSTANCE::start),
            new Optie("A", "Afsluiten", () -> {})
    };

    @Override
    public void start() {
        start(opties);
    }
}
