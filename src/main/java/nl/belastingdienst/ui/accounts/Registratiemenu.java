package nl.belastingdienst.ui.accounts;

import nl.belastingdienst.database.BezorgwijzeDao;
import nl.belastingdienst.ui.algemeen.*;
import nl.belastingdienst.app.accounts.*;
import nl.belastingdienst.database.GebruikerDao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public enum Registratiemenu implements Menu, Afbreekbaar {
    INSTANCE;

    private Gebruiker gebruiker;
    private boolean isAkkoord = false;
    private final EntityManager entityManager = Persistence.createEntityManagerFactory("MySQL-marktplaats").createEntityManager();
    private final Bezorgwijzenkeuzemenu bezorgwijzenkeuzemenu = new Bezorgwijzenkeuzemenu();
    private final Optie[] opties = new Optie[]{
            new Optie("1", "Gebruikersnaam kiezen", this::kiesGebruikersnaam),
            new Optie("2", "Email-adres invoeren", this::voerEmailadresIn),
            new Optie("3", "Adres invoeren", this::voerAdresIn),
            new Optie("4", "Ondersteunde bezorgwijzen kiezen", bezorgwijzenkeuzemenu::start),
            new Optie("5", "[ ] Voorwaarden geaccepteerd", this::accepteerVoorwaarden),
            new Optie("6", "Registratie afronden", this::rondAf),
            new Optie("T", "Terug", () -> {})
    };

    @Override
    public void start() {
        gebruiker = new Gebruiker();
        start(opties);
    }

    @Override
    public void toonOpties() {
        toonOpties(opties);
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

    class Bezorgwijzenkeuzemenu implements Menu, Afbreekbaar {
        BezorgwijzeDao bezorgwijzeDao = BezorgwijzeDao.getInstance(entityManager);

        final List<Bezorgwijze> bezorgwijzen = bezorgwijzeDao.findAll();
        Runnable[] runnables = new Runnable[bezorgwijzen.size()];
        final Optie[] opties = new Optie[bezorgwijzen.size() + 1];

        @Override
        public void start() {
            int j;
            for (int i = 0; i < bezorgwijzen.size() ; i++) {
                int trickingTheCompiler = i;
                runnables[i] = () -> toggle(opties[trickingTheCompiler], bezorgwijzen.get(trickingTheCompiler));
                j = i + 1;
                opties[i] = new Optie("" + j, "[ ] " + bezorgwijzen.get(i).getNaam() + ": " + bezorgwijzen.get(i).getOmschrijving(), runnables[i]);
            }
            opties[bezorgwijzen.size()] = new Optie("T", "Terug", () -> {});

            start(opties);
        }

        @Override
        public void toonOpties() {
            toonOpties(opties);
        }

        public void toggle(Optie optie, Bezorgwijze bezorgwijze) {
            String omschrijving = optie.getOmschrijving();
            if (omschrijving.startsWith("[ ]")) {
                optie.setOmschrijving("[X]" + omschrijving.substring(3));
                gebruiker.addBezorgwijze(bezorgwijze);
            }
            else {
                optie.setOmschrijving("[ ]" + omschrijving.substring(3));
                gebruiker.removeBezorgwijze(bezorgwijze);
            }
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
        if (isAkkoord && gebruiker.valideer()) {
            GebruikerDao.getInstance(entityManager).save(gebruiker);
        } else {
            System.out.println("Ongeldige invoer! Kan niet geregistreerd worden! ");
        }
    }
}
