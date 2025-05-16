package fr.iut.groupe.terraria.demo.modele.ressource;

import fr.iut.groupe.terraria.demo.modele.item.Item;

public class Roche extends Ressource {
    public Roche(double x, double y) {
        super("pierre", 5, x, y,"outil", 25);
    }

    @Override
    public Item getItemProduit() {
        return new Item("Pierre");
    }

    @Override
    public String getNom() {
        return "Roche";
    }
}

