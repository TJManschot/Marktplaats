package nl.belastingdienst.ui.algemeen;

import java.util.Scanner;

public abstract class Menu {
    protected Scanner in = new Scanner(System.in);
    protected Optie[] opties;

    public void draaiMenu() {
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
                System.out.println("\nOngeldige keuze!");
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

        System.out.println(sb);
    }

    private String vraagInvoer() {
        System.out.print("Uw keuze: ");
        return in.nextLine();
    }

    protected boolean afbreken() {
        Scanner in = new Scanner(System.in);

        System.out.print("Weet u zeker dat u deze operatie af wilt breken? (J/N) ");
        String invoer = in.nextLine();

        if (invoer.equals("J"))
            return true;
        if (invoer.equals("N"))
            return false;
        System.out.println("Ongeldige invoer! ");
        return afbreken();
    }
}
