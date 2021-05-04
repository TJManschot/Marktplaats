package nl.belastingdienst;

import nl.belastingdienst.app.accounts.Bezorgwijze;
import nl.belastingdienst.database.BezorgwijzeDao;
import nl.belastingdienst.services.Services;
import nl.belastingdienst.services.printer.SystemPrinter;
import nl.belastingdienst.ui.Hoofdmenu;

import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.List;

public class MarktplaatsApp {
    public static void main(String[] args) {
        Services.INSTANCE
                .entityManager(
                        Persistence
                                .createEntityManagerFactory("MySQL-marktplaats")
                                .createEntityManager())
                .printer(SystemPrinter.INSTANCE);

        MarktplaatsApp marktplaatsApp = new MarktplaatsApp();
        marktplaatsApp.firstStartup(); // Voert noodzakelijke rijen in de database in.

        new Hoofdmenu(Services.INSTANCE.getPrinter()).start();
    }

    public void firstStartup() {
        BezorgwijzeDao bezorgwijzeDao = BezorgwijzeDao.getInstance(Services.INSTANCE.getEntityManager());

        List<Bezorgwijze> opgeslagenBezorgwijzen = bezorgwijzeDao.findAll();

        List<Bezorgwijze> bezorgwijzen = Arrays.asList(
                new Bezorgwijze(
                        "Magazijn",
                        "U brengt het product naar ons magazijn, waar de klant het op kan halen."),
                new Bezorgwijze(
                        "Ophalen",
                        "Het product kan bij u thuis opgehaald worden."),
                new Bezorgwijze(
                        "Versturen",
                        "U stuurt het product per post."),
                new Bezorgwijze(
                        "Versturen onder rembours",
                        "U stuurt het product onder rembours.")
        );

        for (Bezorgwijze bezorgwijze : bezorgwijzen) {
            if (!opgeslagenBezorgwijzen.contains(bezorgwijze))
                bezorgwijzeDao.save(bezorgwijze);
        }
    }
}
