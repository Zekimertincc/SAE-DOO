package fr.iut.groupe.terraria.demo.modele.ressource;

import fr.iut.groupe.terraria.demo.modele.item.Item;
import fr.iut.groupe.terraria.demo.modele.ressource.farm.File;

public class CanneSucre extends Ressource {
    public CanneSucre(double x, double y) {
        super("fil", 5, x, y, "outil", 15);
    }

    @Override
    public Item getItemProduit() {
        return new File();
    }

    @Override
    public String getNom() {
        return "Roche";
    }
}
