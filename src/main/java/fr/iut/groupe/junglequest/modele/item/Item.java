package fr.iut.groupe.junglequest.modele.item;

import fr.iut.groupe.junglequest.modele.personnages.Joueur;

public abstract class Item {
    protected String nom;
    public Item(String nom) {
        this.nom = nom;
    }

    public abstract void ajouter(Joueur joueur, Inventaire inventaire, Item item);

    public abstract int getQuantiteMax();

    public String getNom() {
        return nom;
    }
}
