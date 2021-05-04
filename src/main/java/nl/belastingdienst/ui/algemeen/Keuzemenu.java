package nl.belastingdienst.ui.algemeen;

import nl.belastingdienst.services.printer.Printer;

public abstract class Keuzemenu extends Menu {
    protected boolean[] staatAan;

    public Keuzemenu(Printer printer) {
        super(printer);
    }

    @Override
    protected void toonOpties() {
        StringBuilder sb = new StringBuilder();

        sb.append(getClass().getSimpleName())
                .append("\n");

        for (int i = 0; i < opties.length - 1; i++) {
            sb.append("(")
                    .append(opties[i].getCode())
                    .append(") ");
            if (staatAan[i]) {
                sb.append("[X] ");
            } else {
                sb.append("[ ] ");
            }
            sb.append(opties[i].getOmschrijving())
                    .append("\n");
        }

        sb.append("(")
                .append(opties[opties.length - 1].getCode())
                .append(") ")
                .append(opties[opties.length - 1].getOmschrijving())
                .append("\n");

        printer.print(sb.toString());
    }

    protected void toggle(int index) {
        staatAan[index] = !staatAan[index];
    }
}
