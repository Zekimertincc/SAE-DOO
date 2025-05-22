package fr.iut.groupe.terraria.demo.modele.ressource;

public class Arbre extends Ressource {
    private double x;
    private double y;
    private int vie;
    private final int vieMax = 20;

    public Arbre(double x, double y) {
        super(x, y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getVie() {
        return vie;
    }

    public boolean estDetruit() {
        return vie <= 0;
    }

    public void subirDegats(int degats) {
        vie -= degats;
        if (vie < 0) vie = 0;
    }
    public String getNom(){
        return "Arbre";
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
