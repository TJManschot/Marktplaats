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
        opties = new Optie[]{
                new Optie("1", "Postcode", this::postcodeWijzigen),
                new Optie("2", "Stad", this::stadWijzigen),
                new Optie("3", "Straat", this::straatWijzigen),
                new Optie("4", "Huisnummer", this::huisnummerWijzigen),
                new Optie("T", "Terug", () -> { })
        };
    }

    private void postcodeWijzigen() {
        System.out.println("Voer uw postcode in of kies A om af te breken.");

        String invoer = in.nextLine();
        if (!invoer.equals("A")) {
            adres.setPostcode(invoer);
            opties[0].setOmschrijving("Postcode:   " + invoer);
        }
    }

    private void stadWijzigen() {
        System.out.println("Voer uw stad in of kies A om af te breken.");

        String invoer = in.nextLine();
        if (!invoer.equals("A")) {
            adres.setStad(invoer);
            opties[1].setOmschrijving("Stad:       " + invoer);
        }
    }

    private void straatWijzigen() {
        System.out.println("Voer uw straatnaam in of kies A om af te breken.");

        String invoer = in.nextLine();
        if (!invoer.equals("A")) {
            adres.setStraat(invoer);
            opties[2].setOmschrijving("Straat:     " + invoer);
        }
    }

    private void huisnummerWijzigen() {
        System.out.println("Voer uw huisnummer in of kies A om af te breken.");

        String invoer = in.nextLine();
        if (!invoer.equals("A")) {
            adres.setHuisnummer(invoer);
            opties[3].setOmschrijving("Huisnummer: " + invoer);
        }
    }
}
