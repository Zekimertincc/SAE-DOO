package fr.iut.groupe.junglequest.modele.item.nourriture;

import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.item.Item;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;


public abstract class Nourriture extends Item {
    protected int vie;
    private int quantiteMax;

    public Nourriture(String nom, int vie) {
        super(nom);
        this.vie = vie;
        this.quantiteMax = 10;
    }

    public abstract void appliquerEffetSecondaire(Joueur joueur);
    public void utiliserSur(Joueur joueur) {
        joueur.soigner(this.getVie());
        this.appliquerEffetSecondaire(joueur);
    }

    public abstract void actionRecompense(Joueur joueur, Inventaire inventaire, Item item);

    @Override
    public int getQuantiteMax() {
        return this.quantiteMax;
    }

    public int getVie() {
        return vie;
    }
}

