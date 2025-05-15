package fr.iut.groupe.terraria.demo.modele.nourriture;

import fr.iut.groupe.terraria.demo.modele.Item;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;


public abstract class Nourriture extends Item {
    protected int vie;

    public Nourriture(String nom, int vie) {
        super(nom);
        this.vie = vie;
    }

    // modifie la vie du joueur
    public void utiliserSur(Joueur joueur) {
        joueur.gagnerVie(vie);
    }
    // donne la quantité de vie que donne la nourriture
    public int getVie() {
        return vie;
    }
}

