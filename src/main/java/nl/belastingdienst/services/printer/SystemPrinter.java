package nl.belastingdienst.services.printer;

public enum SystemPrinter implements Printer {
    INSTANCE;

    private void printMetKleur(String string, Kleur kleur) {
        System.out.print(kleur.kleurCode + string);
    }
    private void printlnMetKleur(String string, Kleur kleur) {
        System.out.println(kleur.kleurCode + string);
    }

    @Override
    public void print(String string) {
        printMetKleur(string, Kleur.STANDAARD);
    }
    @Override
    public void println(String string) {
        printlnMetKleur(string, Kleur.STANDAARD);
    }

    @Override
    public void printError(String string) {
        printMetKleur(string, Kleur.LICHTBLAUW);
    }
    @Override
    public void printErrorln(String string) {
        printlnMetKleur(string, Kleur.ROOD);
    }

    @Override
    public void printInfo(String string) {
        printMetKleur(string, Kleur.GEEL);
    }
    @Override
    public void printInfoln(String string) {
        printlnMetKleur(string, Kleur.GEEL);
    }

    @Override
    public void printMetNadruk(String string) {
        printMetKleur(string, Kleur.LICHTBLAUW);
    }
    @Override
    public void printlnMetNadruk(String string) {
        printlnMetKleur(string, Kleur.LICHTBLAUW);
    }
}
