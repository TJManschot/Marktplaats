package nl.belastingdienst.ui.algemeen;

import nl.belastingdienst.services.printer.Printer;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Menu {
    protected Printer printer;
    protected Map<String, Optie> opties = new LinkedHashMap<>();

    public Menu(Printer printer) {
        this.printer = printer;
    }

    public void start() {
        String keuze;
        Optie gekozen;

        while (true) {
            toonMenu();
            keuze = printer.scan();

            gekozen = opties.get(keuze);

            if (gekozen == null) {
                printer.printErrorln("Ongeldige keuze!");
                continue;
            }

            if (opties.get(keuze).getBooleanSupplier().getAsBoolean())
                break;
        }
    }

    protected void toonMenu() {
        printer.print("--- ");
        printer.printMetNadruk(getClass().getSimpleName());
        printer.println(" ---");

        for (Map.Entry<String, Optie> entry : opties.entrySet()) {
            if (entry.getValue() instanceof KeuzeOptie) {
                if (((KeuzeOptie) entry.getValue()).isGekozen())
                    printer.println("(" + entry.getKey() + ") [X] " + entry.getValue().getOmschrijving());
                else
                    printer.println("(" + entry.getKey() + ") [ ] " + entry.getValue().getOmschrijving());
            } else
                printer.println("(" + entry.getKey() + ") " + entry.getValue().getOmschrijving());
        }

        printer.print("\nUw keuze: ");
    }

    protected boolean afbreken() {
        printer.print("Weet u zeker dat u deze operatie af wilt breken? (J/N) ");
        String invoer = printer.scan();

        if (invoer.equals("J"))
            return true;
        if (invoer.equals("N"))
            return false;
        printer.printErrorln("Ongeldige invoer! ");
        return afbreken();
    }
}
