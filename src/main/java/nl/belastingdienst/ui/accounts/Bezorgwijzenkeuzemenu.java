package nl.belastingdienst.ui.accounts;

import nl.belastingdienst.app.accounts.Bezorgwijze;
import nl.belastingdienst.app.accounts.Gebruiker;
import nl.belastingdienst.database.BezorgwijzeDao;
import nl.belastingdienst.services.Services;
import nl.belastingdienst.services.printer.Printer;
import nl.belastingdienst.ui.algemeen.KeuzeOptie;
import nl.belastingdienst.ui.algemeen.Menu;
import nl.belastingdienst.ui.algemeen.Optie;

import java.util.List;

public class Bezorgwijzenkeuzemenu extends Menu {
    Gebruiker gebruiker;
    BezorgwijzeDao bezorgwijzeDao = BezorgwijzeDao.getInstance(Services.INSTANCE.getEntityManager());

    final List<Bezorgwijze> bezorgwijzen = bezorgwijzeDao.findAll();

    public Bezorgwijzenkeuzemenu(Printer printer, Gebruiker gebruiker) {
        super(printer);
        this.gebruiker = gebruiker;

        for (int i = 0; i < bezorgwijzen.size(); i++) {
            final int k = i + 1;
            opties.put("" + k, new KeuzeOptie(
                    bezorgwijzen.get(i).getNaam() + ": " + bezorgwijzen.get(i).getOmschrijving(),
                    () -> {
                        ((KeuzeOptie) opties.get("" + k)).toggle();
                        if (((KeuzeOptie) opties.get("" + k)).isGekozen())
                            gebruiker.addBezorgwijze(bezorgwijzen.get(k-1));
                        else
                            gebruiker.removeBezorgwijze(bezorgwijzen.get(k-1));
                        return false;
                    },
                    false
            ));
        }

        opties.put("T", new Optie("Terug", () -> true));
    }
}
