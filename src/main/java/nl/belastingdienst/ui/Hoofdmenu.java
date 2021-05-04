package nl.belastingdienst.ui;

import nl.belastingdienst.services.printer.Printer;
import nl.belastingdienst.ui.accounts.Registratiemenu;
import nl.belastingdienst.ui.algemeen.Menu;
import nl.belastingdienst.ui.algemeen.Optie;

public class Hoofdmenu extends Menu {
    Registratiemenu registratiemenu;
    
    public Hoofdmenu(Printer printer) {
        super(printer);
        opties = new Optie[]{
                new Optie("1", "Registreren", () -> {
                    registratiemenu = new Registratiemenu(printer);
                    registratiemenu.draaiMenuAf();
                }),
                new Optie("A", "Afsluiten", () -> { })
        };
    }
}
