package nl.belastingdienst.ui.algemeen;

public class Optie {
    private final String code;
    private String omschrijving;
    private final Runnable runnable;

    public Optie(String code, String omschrijving, Runnable runnable) {
        this.code = code;
        this.omschrijving = omschrijving;
        this.runnable = runnable;
    }

    @Override
    public String toString() {
        return "(" + code + ") " + omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getCode() {
        return this.code;
    }

    public Runnable getRunnable() {
        return this.runnable;
    }
}
