package nl.belastingdienst.ui;

import nl.belastingdienst.services.printer.Printer;
import nl.belastingdienst.ui.accounts.Registratiemenu;
import nl.belastingdienst.ui.algemeen.Menu;
import nl.belastingdienst.ui.algemeen.Optie;

public class Hoofdmenu extends Menu {
    Registratiemenu registratiemenu;
    
    public Hoofdmenu(Printer printer) {
        super(printer);

        opties.put("1", new Optie("Registreren", () -> {
            registratiemenu = new Registratiemenu(printer);
            registratiemenu.start();
            return false;
        }));
        opties.put("A", new Optie("Afsluiten", () -> true));
    }
}
