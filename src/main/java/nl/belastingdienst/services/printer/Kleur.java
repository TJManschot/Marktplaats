package nl.belastingdienst.services.printer;

public enum Kleur {
    STANDAARD("\u001B[0m"),
    ZWART("\u001B[30m"),
    ROOD("\u001B[31m"),
    GROEN("\u001B[32m"),
    GEEL("\u001B[33m"),
    BLAUW("\u001B[34m"),
    PAARS("\u001B[35m"),
    LICHTBLAUW("\u001B[36m"),
    WIT("\u001B[37m");

    String kleurCode;

    Kleur(String kleurCode) {
        this.kleurCode = kleurCode;
    }
}
