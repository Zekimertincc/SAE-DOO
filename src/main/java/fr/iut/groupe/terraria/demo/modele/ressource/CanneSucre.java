package fr.iut.groupe.terraria.demo.modele.ressource;

import fr.iut.groupe.terraria.demo.modele.farm.File;

public class CanneSucre extends Ressource {
    public CanneSucre(double x, double y) {
        super(x,y);
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
