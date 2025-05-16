package fr.iut.groupe.terraria.demo.modele.ressource;

import fr.iut.groupe.terraria.demo.modele.Ciblable;
import fr.iut.groupe.terraria.demo.modele.item.Item;
import fr.iut.groupe.terraria.demo.modele.item.equipement.Equipement;

public abstract class Ressource implements Ciblable {
    protected double x, y;
    protected String type;
    protected int quantite;
    protected boolean estRecoltable;
    protected String outilRequis; // couteau, hache, pioche
    protected int vie;
    protected boolean recoltee;

    public Ressource(String type, int quantite, double x, double y, String outilRequis, int vie) {
        this.type = type;
        this.quantite = quantite;
        this.x = x;
        this.y = y;
        this.estRecoltable = true;
        this.outilRequis = outilRequis;
        this.vie = vie;
        this.recoltee = false;
    }
    // recuperer objet bois, pierre..
    public abstract Item getItemProduit();

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
    public void recolter() {
        this.estRecoltable = false;
    }
    public boolean estRecoltable() {
        return estRecoltable;
    }

    // dit si une ressource est recolté ou pas
    @Override
    public void subirDegats(int degats) {
        if (recoltee) return;
        vie -= degats;
        System.out.println("degagagagag");
        if (vie <= 0) {
            recoltee = true;
        }
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public String getType() {
        return type;
    }
    public int getQuantite() {
        return quantite;
    }
}


