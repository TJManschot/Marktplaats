package nl.belastingdienst.ui.algemeen;

import java.util.Scanner;

public interface Afbreekbaar {
    default boolean afbreken() {
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
