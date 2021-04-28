package nl.belastingdienst;

import nl.belastingdienst.app.accounts.*;
import nl.belastingdienst.ui.*;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class MarktplaatsApp {
    public EntityManager entityManager
            = Persistence.createEntityManagerFactory("MySQL-marktplaats").createEntityManager();

    public static void main(String[] args) {
        MarktplaatsApp marktplaatsApp = new MarktplaatsApp();
        marktplaatsApp.firstStartup(); // Voert noodzakelijke rijen in de database in.

        Hoofdmenu.INSTANCE.start();
    }

    public void firstStartup() {
        Bezorgwijze magazijn = new Bezorgwijze("Magazijn", "U brengt het product naar ons magazijn, waar de klant het op kan halen.");
        Bezorgwijze ophalen = new Bezorgwijze("Ophalen", "Het product kan bij u thuis opgehaald worden.");
        Bezorgwijze versturen = new Bezorgwijze("Versturen", "U stuurt het product per post.");
        Bezorgwijze versturenOnderRembours = new Bezorgwijze("Versturen onder rembours", "U stuurt het product onder rembours.");

        entityManager.getTransaction().begin();

        entityManager.persist(magazijn);
        entityManager.persist(ophalen);
        entityManager.persist(versturen);
        entityManager.persist(versturenOnderRembours);

        entityManager.getTransaction().commit();
    }
}
