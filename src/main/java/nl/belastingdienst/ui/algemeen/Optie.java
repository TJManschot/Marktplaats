package nl.belastingdienst.ui.algemeen;

import java.util.function.BooleanSupplier;

public class Optie {
    private String omschrijving;
    private final BooleanSupplier booleanSupplier;

    public Optie(String omschrijving, BooleanSupplier booleanSupplier) {
        this.omschrijving = omschrijving;
        this.booleanSupplier = booleanSupplier;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOmschrijving() { return omschrijving; }

    public BooleanSupplier getBooleanSupplier() {
        return booleanSupplier;
    }
}
