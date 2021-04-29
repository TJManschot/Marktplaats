package nl.belastingdienst.ui.algemeen;

public class Keuzemenu extends Menu {
    protected boolean[] staatAan;

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

        System.out.println(sb);
    }

    protected void toggle(int index) {
        staatAan[index] = !staatAan[index];
    }
}
