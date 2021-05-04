package nl.belastingdienst.ui.accounts;

import nl.belastingdienst.app.accounts.Adres;
import nl.belastingdienst.services.printer.Printer;
import nl.belastingdienst.ui.algemeen.Menu;
import nl.belastingdienst.ui.algemeen.Optie;

public class Adresmenu extends Menu {
    Adres adres;

    public Adresmenu(Printer printer, Adres adres) {
        super(printer);
        this.adres = adres;

        opties.put("1", new Optie("Postcode", this::postcodeWijzigen));
        opties.put("2", new Optie("Stad", this::stadWijzigen));
        opties.put("3", new Optie("Straat", this::straatWijzigen));
        opties.put("4", new Optie("Huisnummer", this::huisnummerWijzigen));
        opties.put("T", new Optie("Terug", () -> true));
    }

    private boolean postcodeWijzigen() {
        System.out.println("Voer uw postcode in of kies A om af te breken.");

        String invoer = printer.scan();
        if (!invoer.equals("A")) {
            adres.setPostcode(invoer);
            opties.get("1").setOmschrijving("Postcode:   " + invoer);
        }

        return false;
    }

    private boolean stadWijzigen() {
        System.out.println("Voer uw stad in of kies A om af te breken.");

        String invoer = printer.scan();
        if (!invoer.equals("A")) {
            adres.setStad(invoer);
            opties.get("2").setOmschrijving("Stad:       " + invoer);
        }

        return false;
    }

    private boolean straatWijzigen() {
        System.out.println("Voer uw straatnaam in of kies A om af te breken.");

        String invoer = printer.scan();
        if (!invoer.equals("A")) {
            adres.setStraat(invoer);
            opties.get("3").setOmschrijving("Straat:     " + invoer);
        }

        return false;
    }

    private boolean huisnummerWijzigen() {
        System.out.println("Voer uw huisnummer in of kies A om af te breken.");

        String invoer = printer.scan();
        if (!invoer.equals("A")) {
            adres.setHuisnummer(invoer);
            opties.get("4").setOmschrijving("Huisnummer: " + invoer);
        }

        return false;
    }
}
