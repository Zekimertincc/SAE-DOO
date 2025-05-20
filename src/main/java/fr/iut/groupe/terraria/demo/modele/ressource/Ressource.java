package fr.iut.groupe.terraria.demo.modele.ressource;

import fr.iut.groupe.terraria.demo.modele.Ciblable;
import fr.iut.groupe.terraria.demo.modele.farm.Farm;
import fr.iut.groupe.terraria.demo.modele.item.Item;
import fr.iut.groupe.terraria.demo.modele.item.equipement.Equipement;

public abstract class Ressource implements Ciblable {
    protected double x, y;
    protected String nom;
    protected int quantite; // farm que contient une ressource
    protected boolean estRecoltable;
    protected String outilRequis; // couteau, hache, pioche
    protected int vie;
    protected boolean recoltee; // on a tout pris ou pas

    public Ressource(String nom, int quantite, double x, double y, String outilRequis, int vie) {
        this.nom = nom;
        this.quantite = quantite;
        this.x = x;
        this.y = y;
        this.estRecoltable = true;
        this.outilRequis = outilRequis;
        this.vie = vie;
        this.recoltee = false;
    }
    // recuperer objet bois, pierre..
    public abstract Farm getItemProduit();

    // verifie si la ressource peut être récoltée avec l'outil fourni
    public boolean peutEtreRecolteeAvec(Equipement outil) {
        boolean peutEtreRecoltee = false;
        if (outil != null) {
            String nomOutil = outil.getNom();
            if (nomOutil.equals("Couteau") || nomOutil.equals("Hache")|| nomOutil.equals("Pioche")) {
                peutEtreRecoltee = true;
            }
        }
        return peutEtreRecoltee;
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
    public String getTypeCible() {
        return "Ressource";
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


