package fr.iut.groupe.terraria.demo.modele.ressource;

import fr.iut.groupe.terraria.demo.modele.item.Item;
import fr.iut.groupe.terraria.demo.modele.farm.File;

public class CanneSucre extends Ressource {
    public CanneSucre(double x, double y) {
        super("CanneSucre", 5, x, y, "Outil", 15);
    }

    @Override
    public File getItemProduit() {
        return new File();
    }

    @Override
    public String getNom() {
        return "CanneSucre";
    }
}
