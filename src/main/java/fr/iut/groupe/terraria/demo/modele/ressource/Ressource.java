package fr.iut.groupe.terraria.demo.modele.ressource;
import fr.iut.groupe.terraria.demo.modele.Item;

public abstract class Ressource {
    protected double x, y;
    protected String type;
    protected int quantite;
    protected boolean estRecoltable;

    public Ressource(String type, int quantite, double x, double y) {
        this.type = type;
        this.quantite = quantite;
        this.x = x;
        this.y = y;
        this.estRecoltable = true;
    }
    public boolean estRecoltable() {
        return estRecoltable;
    }

    public void recolter() {
        this.estRecoltable = false;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public abstract Item getItemProduit();


    public String getType() {
        return type;
    }

    public int getQuantite() {
        return quantite;
    }
}


