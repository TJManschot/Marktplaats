package nl.belastingdienst.ui.algemeen;

import java.util.Scanner;

public interface Menu {
    Scanner in = new Scanner(System.in);

    void start();
    default void start(Optie[] opties) {
        toonOpties();

        String keuze;
        while(!(keuze = vraagInvoer("Uw keuze: ")).equals(opties[opties.length-1].getCode())) {
            for(Optie optie : opties) {
                if(keuze.equals(optie.getCode())) {
                    optie.getRunnable().run();
                    toonOpties();
                    keuze = "";
                    break;
                }
            }

            if(!keuze.isEmpty())
                System.out.println("\nOngeldige keuze!");
        }
        opties[opties.length-1].getRunnable().run();
    }

    void toonOpties();
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
