package fr.iut.groupe.terraria.demo.modele.ressource;

import fr.iut.groupe.terraria.demo.modele.item.Item;

public class CanneSucre extends Ressource {
    public CanneSucre(double x, double y) {
        super("fil", 5, x, y, "outil", 15);
    }

    @Override
    public Item getItemProduit() {
        return new Item("Fil");
    }

    @Override
    public String getNom() {
        return "Roche";
    }
}
