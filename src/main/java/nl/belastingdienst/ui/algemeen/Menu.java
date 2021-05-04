package nl.belastingdienst.ui.algemeen;

import nl.belastingdienst.services.printer.Printer;
import nl.belastingdienst.services.Services;

import java.util.Scanner;

public abstract class Menu {
    protected Scanner in = new Scanner(System.in);
    protected Printer printer;
    protected Optie[] opties;

    public Menu(Printer printer) {
        this.printer = printer;
    }

    public void draaiMenuAf() {
        String keuze;

        while (true) {
            toonOpties();
            keuze = vraagInvoer();

            if (keuze.equals(opties[opties.length - 1].getCode())) {
                opties[opties.length-1].getRunnable().run();
                break;
            }

            for (Optie optie : opties) {
                if (keuze.equals(optie.getCode())) {
                    optie.getRunnable().run();
                    keuze = "";
                    break;
                }
            }

            if (!keuze.isEmpty())
                printer.printErrorln("\nOngeldige keuze!");
        }
    }

    protected void toonOpties() {
        StringBuilder sb = new StringBuilder();

        sb.append(getClass().getSimpleName())
                .append("\n");

        for (Optie optie : opties) {
            sb.append("(")
                    .append(optie.getCode())
                    .append(") ")
                    .append(optie.getOmschrijving())
                    .append("\n");
        }

        printer.print(sb.toString());
    }

    private String vraagInvoer() {
        System.out.print("Uw keuze: ");
        return in.nextLine();
    }

    protected boolean afbreken() {
        Scanner in = new Scanner(System.in);

        printer.print("Weet u zeker dat u deze operatie af wilt breken? (J/N) ");
        String invoer = in.nextLine();

        if (invoer.equals("J"))
            return true;
        if (invoer.equals("N"))
            return false;
        printer.printErrorln("Ongeldige invoer! ");
        return afbreken();
    }
}
