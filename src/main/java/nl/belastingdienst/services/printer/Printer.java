package nl.belastingdienst.services.printer;

public interface Printer {
    void print(String string);
    void println(String string);

    void printError(String string);
    void printErrorln(String string);

    void printInfo(String string);
    void printInfoln(String string);

    void printMetNadruk(String string);
    void printlnMetNadruk(String string);
}
