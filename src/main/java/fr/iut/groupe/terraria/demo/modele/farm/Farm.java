package fr.iut.groupe.terraria.demo.modele.farm;

import fr.iut.groupe.terraria.demo.modele.Inventaire;
import fr.iut.groupe.terraria.demo.modele.item.Item;

public abstract class Farm extends Item {
    private int quantiteMax; // 50 max par type dans l'inventaire

    public Farm(String nom) {
        super(nom);
        this.quantiteMax = 10;
    }

    @Override
    public int getQuantiteMax() {
        return quantiteMax;
    }

}
