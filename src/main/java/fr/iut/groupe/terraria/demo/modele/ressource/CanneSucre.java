package fr.iut.groupe.terraria.demo.modele.ressource;

import fr.iut.groupe.terraria.demo.modele.Item;

public class CanneSucre extends Ressource {
    public CanneSucre(double x, double y) {
        super("fil", 5, x, y);
    }

    @Override
    public Item getItemProduit() {
        return new Item("Fil");
    }
}
