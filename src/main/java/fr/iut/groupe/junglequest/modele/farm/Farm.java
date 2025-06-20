package fr.iut.groupe.junglequest.modele.farm;

import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.item.Item;

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
