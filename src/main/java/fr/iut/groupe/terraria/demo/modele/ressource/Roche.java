package fr.iut.groupe.terraria.demo.modele.ressource;

import fr.iut.groupe.terraria.demo.modele.farm.Pierre;

public class Roche extends Ressource {
    public Roche(double x, double y) {
        super(x,y);
    }

    @Override
    public Pierre getItemProduit() {
        return new Pierre();
    }

    @Override
    public String getNom() {
        return "Roche";
    }
}

