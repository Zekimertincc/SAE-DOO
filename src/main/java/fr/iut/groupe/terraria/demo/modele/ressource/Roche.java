package fr.iut.groupe.terraria.demo.modele.ressource;

import fr.iut.groupe.terraria.demo.modele.item.Item;
import fr.iut.groupe.terraria.demo.modele.ressource.farm.Pierre;

public class Roche extends Ressource {
    public Roche(double x, double y) {
        super("Roche", 5, x, y,"Outil", 25);
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

