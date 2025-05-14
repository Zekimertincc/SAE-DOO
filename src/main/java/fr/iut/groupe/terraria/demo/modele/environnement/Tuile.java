package fr.iut.groupe.terraria.demo.modele.environnement;

public class Tuile {
    private String type;
    private boolean estAccessible;

    public Tuile(String type, boolean estAccessible) {
        this.type = type;
        this.estAccessible = estAccessible;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEstAccessible() {
        return estAccessible;
    }

    public void setEstAccessible(boolean estAccessible) {
        this.estAccessible = estAccessible;
    }

    @Override
    public String toString() {
        return "Tuile{" +
                "type='" + type + '\'' +
                ", estAccessible=" + estAccessible +
                '}';
    }
}
