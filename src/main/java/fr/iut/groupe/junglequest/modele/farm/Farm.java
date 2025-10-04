package fr.iut.groupe.junglequest.modele.farm;

import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.item.Item;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;

public abstract class Farm extends Item {
    private int quantiteMax; // 50 max par type dans l'inventaire

    public Farm(String nom) {
        super(nom);
        this.quantiteMax = 10;
    }
    @Override
    public void ajouter (Joueur joueur, Inventaire inventaire, Item item){
        inventaire.ajouterItem(item.getNom(), 20);
    }
    @Override
    public int getQuantiteMax() {
        return quantiteMax;
    }


}
