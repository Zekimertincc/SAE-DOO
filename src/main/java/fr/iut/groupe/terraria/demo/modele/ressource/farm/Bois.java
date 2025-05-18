package fr.iut.groupe.terraria.demo.modele.ressource.farm;

import fr.iut.groupe.terraria.demo.modele.item.Item;

public class Bois extends Item{
    public Bois() {
        super("Bois");
    }

    @Override
    public int getQuantiteMax() {
        return -1;
    }
}