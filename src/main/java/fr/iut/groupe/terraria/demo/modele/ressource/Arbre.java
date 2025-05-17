package fr.iut.groupe.terraria.demo.modele.ressource;

import fr.iut.groupe.terraria.demo.modele.item.Item;
import fr.iut.groupe.terraria.demo.modele.ressource.farm.Bois;

public class Arbre extends Ressource {
    public Arbre(double x, double y) {
        super("bois", 5, x, y,"outil", 20);
    }

    @Override
    public Item getItemProduit() {
        return new Bois();
    }

    @Override
    public String getNom() {
        return "Arbre";
    }
}
