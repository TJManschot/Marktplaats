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
        opties = new Optie[]{
                new Optie("1", "Gebruikersnaam kiezen", this::kiesGebruikersnaam),
                new Optie("2", "Email-adres invoeren", this::voerEmailadresIn),
                new Optie("3", "Adres invoeren", this::voerAdresIn),
                new Optie("4", "Ondersteunde bezorgwijzen kiezen", bezorgwijzenkeuzemenu::draaiMenuAf),
                new Optie("5", "[ ] Voorwaarden geaccepteerd", this::accepteerVoorwaarden),
                new Optie("6", "Registratie afronden", this::rondAf),
                new Optie("T", "Terug", () -> { })
        };
    }

    public void kiesGebruikersnaam() {
        Gebruikersnaam gebruikersnaam = new Gebruikersnaam();
        String invoer;

        //noinspection ConditionalBreakInInfiniteLoop
        while (true) {
            System.out.println("Kies een gebruikersnaam of kies A om af te breken");

            invoer = in.nextLine();
            if (invoer.equals("A"))
                return;

            gebruikersnaam.setGebruikersnaam(invoer);
            if (gebruikerValidator.valideer(gebruikersnaam))
                break;
        }

        opties[0].setOmschrijving("Gebruikersnaam: " + gebruikersnaam.getGebruikersnaam());
        gebruiker.setGebruikersnaam(gebruikersnaam);
    }

    public void voerEmailadresIn() {
        Email email = new Email();
        String invoer;

        while (true) {
            System.out.println("Voer uw emailadres in of kies A om af te breken");

            invoer = in.nextLine();
            if (invoer.equals("A")) {
                if (afbreken())
                    return;
                continue;
            }

            email.setEmail(invoer);
            if (gebruikerValidator.valideer(email))
                break;

            System.out.println("Ongeldig emailadres!");
        }

        opties[1].setOmschrijving("Email: " + email.getEmail());
        gebruiker.setEmail(email);
    }

    public void voerAdresIn() {
        Adres adres = new Adres();
        if (gebruiker.getAdres() != null)
            adres = gebruiker.getAdres();

        new Adresmenu(printer, adres).draaiMenuAf();
        if (gebruikerValidator.valideer(adres)) {
            gebruiker.setAdres(adres);
            opties[2].setOmschrijving(
                    "Postcode: " + adres.getPostcode() + " " + adres.getStad() +
                            "\n    Straat:   " + adres.getStraat() + " " + adres.getHuisnummer()
            );
        }
    }

    public void accepteerVoorwaarden() {
        while (true) {
            System.out.print("Accepteert u de voorwaarden? (J/N) ");
            String invoer = in.nextLine();

            if (invoer.equals("J")) {
                isAkkoord = true;
                opties[4].setOmschrijving("[X] Voorwaarden geaccepteerd");
                break;
            }

            if (invoer.equals("N")) {
                isAkkoord = false;
                opties[4].setOmschrijving("[ ] Voorwaarden geaccepteerd");
                break;
            }

            System.out.println("Ongeldige invoer!");
        }
    }

    public void rondAf() {
        if (isAkkoord && gebruikerValidator.valideer(gebruiker)) {
            System.out.println("Uw gegevens worden opgeslagen!");
            GebruikerDao.getInstance(Services.INSTANCE.getEntityManager()).save(gebruiker);
        } else {
            System.out.println("Ongeldige invoer! Kan niet geregistreerd worden! ");
        }
    }
}
