package fr.iut.groupe.terraria.demo.modele.ressource;

public abstract class Ressource {
    protected double x, y;

    public Ressource(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public abstract String getNom(); // örnek: "Arbre"
}
