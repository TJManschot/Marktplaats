package nl.belastingdienst.ui.accounts;

import nl.belastingdienst.MarktplaatsApp;
import nl.belastingdienst.database.BezorgwijzeDao;
import nl.belastingdienst.app.accounts.*;
import nl.belastingdienst.ui.algemeen.*;

import javax.persistence.EntityManager;
import java.util.List;

public class Bezorgwijzenkeuzemenu extends Keuzemenu {
    Gebruiker gebruiker;
    EntityManager entityManager = MarktplaatsApp.entityManager;

    BezorgwijzeDao bezorgwijzeDao = BezorgwijzeDao.getInstance(entityManager);

    final List<Bezorgwijze> bezorgwijzen = bezorgwijzeDao.findAll();

    public Bezorgwijzenkeuzemenu(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
        opties = new Optie[bezorgwijzen.size() + 1];

        staatAan = new boolean[bezorgwijzen.size()];
        int j;
        for (int i = 0; i < bezorgwijzen.size() ; i++) {
            int trickingTheCompiler = i;
            j = i + 1;
            opties[i] = new Optie(
                    "" + j,
                    bezorgwijzen.get(i).getNaam() + ": " + bezorgwijzen.get(i).getOmschrijving(),
                    () -> toggle(trickingTheCompiler));
        }
        opties[bezorgwijzen.size()] = new Optie("T", "Terug", () -> {
            for (int i = 0; i < bezorgwijzen.size(); i++) {
                if (staatAan[i]) {
                    gebruiker.addBezorgwijze(bezorgwijzen.get(i));
                } else {
                    gebruiker.removeBezorgwijze(bezorgwijzen.get(i));
                }
            }
        });
    }

    public void start() {
        draaiMenu();
    }
}
