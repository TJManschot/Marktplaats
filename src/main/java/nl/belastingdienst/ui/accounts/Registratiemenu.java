package nl.belastingdienst.ui.accounts;

import nl.belastingdienst.ui.algemeen.*;
import nl.belastingdienst.app.accounts.*;
import nl.belastingdienst.database.GebruikerDao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.Locale;

public class Registratiemenu extends Menu {
    private Gebruiker gebruiker;
    private boolean isAkkoord = false;
    private final EntityManager entityManager = Persistence.createEntityManagerFactory("MySQL-marktplaats").createEntityManager();
    private final Bezorgwijzenkeuzemenu bezorgwijzenkeuzemenu = new Bezorgwijzenkeuzemenu();
    private final Optie[] opties = new Optie[]{
            new Optie("1", "Gebruikersnaam kiezen", this::kiesGebruikersnaam),
            new Optie("2", "Email-adres invoeren", this::voerEmailadresIn),
            new Optie("3", "Adres invoeren", this::voerAdresIn),
            new Optie("4", "Ondersteunde bezorgwijzen kiezen", () -> new Bezorgwijzenkeuzemenu().start(gebruiker)),
            new Optie("5", "[ ] Voorwaarden geaccepteerd", this::accepteerVoorwaarden),
            new Optie("6", "Registratie afronden", this::rondAf),
            new Optie("T", "Terug", () -> {})
    };

    public void start() {
        gebruiker = new Gebruiker();
        start(opties);
    }

    public void kiesGebruikersnaam() {
        Gebruikersnaam gebruikersnaam = new Gebruikersnaam();
        String invoer;

        while (true) {
            System.out.println("Kies een gebruikersnaam of kies A om af te breken");

            invoer = in.nextLine();
            if (invoer.equals("A")) {
                if (afbreken())
                    return;
                continue;
            }

            gebruikersnaam.setGebruikersnaam(invoer);
            if (gebruikersnaam.valideer())
                break;

            System.out.println("Ongeldige gebruikersnaam!");
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
            if (email.valideer())
                break;

            System.out.println("Ongeldig emailadres!");
        }

        opties[1].setOmschrijving("Email: " + email.getEmail());
        gebruiker.setEmail(email);
    }

    public void voerAdresIn() {
        Adres adres = new Adres();
        String invoer;

        while (true) {
            System.out.println("Voer uw postcode in of kies A om af te breken.");

            invoer = in.nextLine();
            if (invoer.equals("A")) {
                if (afbreken())
                    return;
                continue;
            }

            adres.setPostcode(invoer);

            System.out.println("Voer uw stad in of kies A om af te breken.");

            invoer = in.nextLine();
            if (invoer.equals("A")) {
                if (afbreken())
                    return;
                continue;
            }

            adres.setStad(invoer);

            System.out.println("Voer uw straatnaam in of kies A om af te breken.");

            invoer = in.nextLine();
            if (invoer.equals("A")) {
                if (afbreken())
                    return;
                continue;
            }

            adres.setStraat(invoer);

            System.out.println("Voer uw huisnummer in of kies A om af te breken.");

            invoer = in.nextLine();
            if (invoer.equals("A")) {
                if (afbreken())
                    return;
                continue;
            }

            adres.setHuisnummer(invoer);

            if (adres.valideer())
                break;

            System.out.println("Ongeldig adres! ");
        }

        opties[2].setOmschrijving("Postcode: " + adres.getPostcode() + " " + adres.getStad() +
                "\n    Straat:   " + adres.getStraat() + " " + adres.getHuisnummer());
        gebruiker.setAdres(adres);
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
        boolean voorwaarde = isAkkoord && gebruiker.valideer();
        for (Bezorgwijze bezorgwijze : gebruiker.getBezorgwijzen()) {
            if (bezorgwijze.getNaam().toLowerCase(Locale.ROOT).equals("ophalen"))
                voorwaarde = voorwaarde && gebruiker.getAdres() != null;
        }

        if (voorwaarde) {
            GebruikerDao.getInstance(entityManager).save(gebruiker);
        } else {
            System.out.println("Ongeldige invoer! Kan niet geregistreerd worden! ");
        }
    }
}
