package fr.iut.groupe.terraria.demo.modele.ressource.farm;

import fr.iut.groupe.terraria.demo.modele.item.Item;

public class Pierre extends Item{

    public Pierre() {
        super("Pierre");
    }

    @Override
    public int getQuantiteMax() {
        return -1;
    }
}
