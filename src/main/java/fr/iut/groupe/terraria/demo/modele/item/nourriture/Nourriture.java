package fr.iut.groupe.terraria.demo.modele.item.nourriture;

import fr.iut.groupe.terraria.demo.modele.item.Item;

public class Nourriture extends Item {
    private int vie;

    public Nourriture(String nom, int vie) {
        super(nom);
        this.vie = vie;
    }

    public int getVie() {
        return vie;
    }

    public boolean estPositive() {
        return vie > 0;
    }
}