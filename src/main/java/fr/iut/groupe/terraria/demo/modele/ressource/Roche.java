package fr.iut.groupe.terraria.demo.modele.ressource;

import fr.iut.groupe.terraria.demo.modele.item.Item;
import fr.iut.groupe.terraria.demo.modele.ressource.farm.Pierre;

public class Roche extends Ressource {
    public Roche(String nom, double x, double y) {
        super(nom,"pierre", 5, x, y,"outil", 25);
    }
    @Override
    public Item getItemProduit() {
        return new Pierre();
    }
    @Override
    public String getNom() {
        return "Roche";
    }
}

