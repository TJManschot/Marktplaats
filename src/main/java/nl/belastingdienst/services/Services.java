package nl.belastingdienst.services;

import nl.belastingdienst.services.printer.Printer;

import javax.persistence.EntityManager;

public enum Services {
    INSTANCE;

    private EntityManager entityManager;
    private Printer printer;

    public Services entityManager(EntityManager entityManager) {
        if (this.entityManager != null)
            throw new IllegalArgumentException("Cannot overwrite previous entity manager.");
        this.entityManager = entityManager;
        return this;
    }

    public Services printer(Printer printer) {
        if (this.printer != null)
            throw new IllegalArgumentException("Cannot overwrite previous printer.");
        this.printer = printer;
        return this;
    }

    public EntityManager getEntityManager() {
        if (entityManager == null)
            throw new NullPointerException("Entity manager not set.");
        return entityManager;
    }

    public Printer getPrinter() {
        if (printer == null)
            throw new NullPointerException("Printerservie not set.");
        return printer;
    }
}
