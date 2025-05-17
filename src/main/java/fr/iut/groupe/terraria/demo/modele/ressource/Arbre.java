package fr.iut.groupe.terraria.demo.modele.ressource;

import fr.iut.groupe.terraria.demo.modele.item.Item;

public class Arbre extends Ressource {
    public Arbre(double x, double y) {
        super("bois", 5, x, y,"outil", 20);
    }

    @Override
    public Item getItemProduit() {
        return new Item("Bois");
    }

    @Override
    public String getNom() {
        return "Arbre";
    }

}
