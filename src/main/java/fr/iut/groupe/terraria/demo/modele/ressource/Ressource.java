package fr.iut.groupe.terraria.demo.modele.ressource;

import fr.iut.groupe.terraria.demo.modele.Ciblable;
import fr.iut.groupe.terraria.demo.modele.item.Item;
import fr.iut.groupe.terraria.demo.modele.item.equipement.Equipement;

public abstract class Ressource extends Item implements Ciblable {
    protected double x, y;
    protected int quantite; // farm que contient une ressource
    protected boolean estRecoltable;
    protected String outilRequis; // couteau, hache, pioche
    protected int vie;
    protected boolean recoltee; // on a tout pris ou pas
    private int quantiteMax; // 50 max par type dans l'inventaire

    public Ressource(String nom, int quantite, double x, double y, String outilRequis, int vie) {
        super(nom);
        this.nom = nom;
        this.quantite = quantite;
        this.x = x;
        this.y = y;
        this.estRecoltable = true;
        this.outilRequis = outilRequis;
        this.vie = vie;
        this.recoltee = false;
        this.quantiteMax = 50;
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

    public boolean estRecoltee() {
        return recoltee;
    }
    // dit si une ressource est recolté ou pas
    @Override
    public void subirDegats(int degats) {
        if (!recoltee){
            vie -= degats;
        }
        if (vie <= 0) {
            recoltee = true;
        }
    }
    @Override
    public int getQuantiteMax() {
        return this.quantiteMax;
    }
// -----------------------------------------------------------------------------------------------------------

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public int getQuantite() {
        return quantite;
    }
    public int getVie() {
        return vie;
    }
}


