package fr.iut.groupe.terraria.demo.modele.item.nourriture;

import fr.iut.groupe.terraria.demo.modele.item.Item;
<<<<<<< HEAD

public class Nourriture extends Item {
    private int vie;
=======
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;


public abstract class Nourriture extends Item {
    protected int vie;
    private int quantiteMax;
>>>>>>> antoine

    public Nourriture(String nom, int vie) {
        super(nom);
        this.vie = vie;
<<<<<<< HEAD
=======
        this.quantiteMax = 10;
    }
    @Override
    public int getQuantiteMax() {
        return this.quantiteMax;
    }
    // modifie la vie du joueur
    public void utiliserSur(Joueur joueur) {
        joueur.gagnerVie(vie);
>>>>>>> antoine
    }

    public int getVie() {
        return vie;
    }
<<<<<<< HEAD

    public boolean estPositive() {
        return vie > 0;
    }
}
=======
}

>>>>>>> antoine
