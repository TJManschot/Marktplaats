package nl.belastingdienst.ui.accounts;

import nl.belastingdienst.database.BezorgwijzeDao;
import nl.belastingdienst.app.accounts.*;
import nl.belastingdienst.services.Services;
import nl.belastingdienst.services.printer.Printer;
import nl.belastingdienst.ui.algemeen.*;

import java.util.List;

public class Bezorgwijzenkeuzemenu extends Keuzemenu {
    Gebruiker gebruiker;
    BezorgwijzeDao bezorgwijzeDao = BezorgwijzeDao.getInstance(Services.INSTANCE.getEntityManager());

    final List<Bezorgwijze> bezorgwijzen = bezorgwijzeDao.findAll();

    public Bezorgwijzenkeuzemenu(Printer printer, Gebruiker gebruiker) {
        super(printer);
        this.gebruiker = gebruiker;
        opties = new Optie[bezorgwijzen.size() + 1];
        staatAan = new boolean[bezorgwijzen.size()];

        int j;
        for (int i = 0; i < bezorgwijzen.size() ; i++) {
            final int k = i;
            j = i + 1;
            opties[i] = new Optie(
                    "" + j,
                    bezorgwijzen.get(i).getNaam() + ": " + bezorgwijzen.get(i).getOmschrijving(),
                    () -> toggle(k));
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
}
