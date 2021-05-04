package nl.belastingdienst.ui.accounts;

import nl.belastingdienst.services.GebruikerValidator;
import nl.belastingdienst.services.Services;
import nl.belastingdienst.services.printer.Printer;
import nl.belastingdienst.ui.algemeen.*;
import nl.belastingdienst.app.accounts.*;
import nl.belastingdienst.database.GebruikerDao;

public class Registratiemenu extends Menu {
    private final Gebruiker gebruiker = new Gebruiker();
    private final GebruikerValidator gebruikerValidator = new GebruikerValidator(GebruikerDao.getInstance(Services.INSTANCE.getEntityManager()), Services.INSTANCE.getPrinter());
    private final Bezorgwijzenkeuzemenu bezorgwijzenkeuzemenu = new Bezorgwijzenkeuzemenu(printer, gebruiker);
    private boolean isAkkoord = false;

    public Registratiemenu(Printer printer) {
        super(printer);

        opties.put("1", new Optie("Gebruikersnaam kiezen", this::kiesGebruikersnaam));
        opties.put("2", new Optie("Email-adres invoeren", this::voerEmailadresIn));
        opties.put("3", new Optie("Adres invoeren", this::voerAdresIn));
        opties.put("4", new Optie("Ondersteunde bezorgwijzen kiezen", this::kiesBezorgwijzen));
        opties.put("5", new KeuzeOptie("Voorwaarden geaccepteerd", this::accepteerVoorwaarden, false));
        opties.put("6", new Optie("Registratie afronden", this::rondAf));
        opties.put("T", new Optie("Terug", () -> true));
    }

    public boolean kiesGebruikersnaam() {
        Gebruikersnaam gebruikersnaam = new Gebruikersnaam();
        String invoer;

        //noinspection ConditionalBreakInInfiniteLoop
        while (true) {
            printer.printlnMetNadruk("Kies een gebruikersnaam of kies A om af te breken");

            invoer = printer.scan();
            if (invoer.equals("A"))
                return false;

            gebruikersnaam.setGebruikersnaam(invoer);
            if (gebruikerValidator.valideer(gebruikersnaam))
                break;
        }

        opties.get("1").setOmschrijving("Gebruikersnaam: " + gebruikersnaam.getGebruikersnaam());
        gebruiker.setGebruikersnaam(gebruikersnaam);

        return false;
    }

    public boolean voerEmailadresIn() {
        Email email = new Email();
        String invoer;

        while (true) {
            printer.printlnMetNadruk("Voer uw emailadres in of kies A om af te breken");

            invoer = printer.scan();
            if (invoer.equals("A")) {
                if (afbreken())
                    return false;
                continue;
            }

            email.setEmail(invoer);
            if (gebruikerValidator.valideer(email))
                break;
        }

        opties.get("2").setOmschrijving("Email: " + email.getEmail());
        gebruiker.setEmail(email);

        return false;
    }

    public boolean voerAdresIn() {
        Adres adres = new Adres();
        if (gebruiker.getAdres() != null)
            adres = gebruiker.getAdres();

        new Adresmenu(printer, adres).start();
        if (gebruikerValidator.valideer(adres)) {
            gebruiker.setAdres(adres);
            opties.get("3").setOmschrijving(
                    "Postcode: " + adres.getPostcode() + " " + adres.getStad() +
                            "\n    Straat:   " + adres.getStraat() + " " + adres.getHuisnummer()
            );
        }

        return false;
    }

    public boolean kiesBezorgwijzen() {
        bezorgwijzenkeuzemenu.start();
        return false;
    }

    public boolean accepteerVoorwaarden() {
        ((KeuzeOptie) opties.get("5")).toggle();
        isAkkoord = ((KeuzeOptie) opties.get("5")).isGekozen();

        return false;
    }

    public boolean rondAf() {
        if (isAkkoord && gebruikerValidator.valideer(gebruiker)) {
            printer.printInfoln("Uw gegevens worden opgeslagen!");
            GebruikerDao.getInstance(Services.INSTANCE.getEntityManager()).save(gebruiker);
            return true;
        }
        return false;
    }
}
