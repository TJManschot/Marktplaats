package nl.belastingdienst.ui.algemeen;

import java.util.Scanner;

public interface Menu {
    Scanner in = new Scanner(System.in);

    void start();
    default void start(Optie[] opties) {
        String keuze;

        while (true) {
            toonOpties(opties);
            keuze = vraagInvoer("Uw keuze: ");

            if (keuze.equals(opties[opties.length - 1].getCode()))
                break;

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

    default void toonOpties(Optie[] opties) {
        StringBuilder sb = new StringBuilder();

        sb.append(getClass().getSimpleName())
                .append("\n");

        for (Optie optie : opties) {
            sb.append(optie.toString())
                    .append("\n");
        }

        System.out.println(sb);
    }

    default String vraagInvoer(String bericht) {
        System.out.print(bericht);
        return in.nextLine();
    }
}
