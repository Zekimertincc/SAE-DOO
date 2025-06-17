package fr.iut.groupe.terraria.demo.modele.ressource;

import fr.iut.groupe.terraria.demo.modele.farm.Bois;

public class Arbre extends Ressource {
    public Arbre(double x, double y) {
        super("Arbre", 5, x, y,"Outil", 20);
    }

    @Override
    public Bois getItemProduit() {
        return new Bois();
    }

    @Override
    public String getNom() {
        return "Arbre";
    }
}
