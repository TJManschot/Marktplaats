package nl.belastingdienst.ui.algemeen;

import java.util.function.BooleanSupplier;

public class KeuzeOptie extends Optie {
    private boolean gekozen;

    public KeuzeOptie(String omschrijving, BooleanSupplier booleanSupplier, boolean gekozen) {
        super(omschrijving, booleanSupplier);
        this.gekozen = gekozen;
    }

    public void toggle() { gekozen = !gekozen; }

    public boolean isGekozen() { return gekozen; }
}
