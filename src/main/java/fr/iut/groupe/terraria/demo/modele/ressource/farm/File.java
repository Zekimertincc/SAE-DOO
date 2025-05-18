package fr.iut.groupe.terraria.demo.modele.ressource.farm;

import fr.iut.groupe.terraria.demo.modele.item.Item;

public class File extends Item{

    public File() {
        super("File");
    }

    @Override
    public int getQuantiteMax() {
        return -1;
    }
}