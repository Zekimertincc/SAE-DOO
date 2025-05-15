package fr.iut.groupe.terraria.demo.modele.ressource;

import fr.iut.groupe.terraria.demo.modele.Item;

public class Roche extends Ressource {
    public Roche(double x, double y) {
        super("pierre", 5, x, y);
    }

    @Override
    public Item getItemProduit() {
        return new Item("Pierre");
    }
}

