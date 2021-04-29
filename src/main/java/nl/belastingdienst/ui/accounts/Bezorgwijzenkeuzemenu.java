package nl.belastingdienst.ui.accounts;

import nl.belastingdienst.MarktplaatsApp;
import nl.belastingdienst.app.accounts.Bezorgwijze;
import nl.belastingdienst.app.accounts.Gebruiker;
import nl.belastingdienst.database.BezorgwijzeDao;
import nl.belastingdienst.ui.algemeen.Menu;
import nl.belastingdienst.ui.algemeen.Optie;

import javax.persistence.EntityManager;
import java.util.List;

public class Bezorgwijzenkeuzemenu extends Menu {
    Gebruiker gebruiker;
    EntityManager entityManager = MarktplaatsApp.entityManager;

    BezorgwijzeDao bezorgwijzeDao = BezorgwijzeDao.getInstance(entityManager);

    final List<Bezorgwijze> bezorgwijzen = bezorgwijzeDao.findAll();

    public Bezorgwijzenkeuzemenu(Gebruiker gebruiker) {
        int j;
        this.gebruiker = gebruiker;
        Runnable[] runnables = new Runnable[bezorgwijzen.size()];
        opties = new Optie[bezorgwijzen.size() + 1];
        for (int i = 0; i < bezorgwijzen.size() ; i++) {
            int trickingTheCompiler = i;
            runnables[i] = () -> toggle(opties[trickingTheCompiler], bezorgwijzen.get(trickingTheCompiler));
            j = i + 1;
            opties[i] = new Optie("" + j, "[ ] " + bezorgwijzen.get(i).getNaam() + ": " + bezorgwijzen.get(i).getOmschrijving(), runnables[i]);
        }
        opties[bezorgwijzen.size()] = new Optie("T", "Terug", () -> {});
    }

    public void start() {
        draaiMenu();
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
