package fr.iut.groupe.terraria.demo.modele.item.nourriture;

import fr.iut.groupe.terraria.demo.modele.item.Item;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;


public abstract class Nourriture extends Item {
    protected int vie;
    private int quantiteMax;

    public Nourriture(String nom, int vie) {
        super(nom);
        this.vie = vie;
        this.quantiteMax = 10;
    }
    @Override
    public int getQuantiteMax() {
        return this.quantiteMax;
    }
    // modifie la vie du joueur
    public void utiliserSur(Joueur joueur) {
        joueur.gagnerVie(vie);
    }

    public int getVie() {
        return vie;
    }
}

