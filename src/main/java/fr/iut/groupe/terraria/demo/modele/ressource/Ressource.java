package fr.iut.groupe.terraria.demo.modele.ressource;

import fr.iut.groupe.terraria.demo.modele.Item;
import fr.iut.groupe.terraria.demo.modele.equipement.Equipement;

public abstract class Ressource {
    protected double x, y;
    protected String type;
    protected int quantite;
    protected boolean estRecoltable;
    protected String outilRequis; // couteau, hache, pioche

    public Ressource(String type, int quantite, double x, double y, String outilRequis) {
        this.type = type;
        this.quantite = quantite;
        this.x = x;
        this.y = y;
        this.estRecoltable = true;
        this.outilRequis = outilRequis;
    }

    // verifie si la ressource peut être récoltée avec l'outil fourni
    public boolean peutEtreRecolteeAvec(Equipement outil) {
        if (outilRequis == null) return true;  // pas besoin d'outil
        if (outil == null) return false;       // pas d'outil, pas possible

        String nomOutil = outil.getNom();

        if (nomOutil.equals("Couteau") || nomOutil.equals("couteau") ||
                nomOutil.equals("Hache") || nomOutil.equals("hache") ||
                nomOutil.equals("Pioche") || nomOutil.equals("pioche")) {
            return true;
        }
        return false;
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


