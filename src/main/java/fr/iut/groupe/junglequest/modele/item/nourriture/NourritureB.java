package fr.iut.groupe.junglequest.modele.item.nourriture;

import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.item.Item;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;

public abstract class NourritureB extends Nourriture{
    public NourritureB(String nom, int vie) {
        super(nom, vie);
    }
    @Override
    public void actionRecompense(Joueur joueur, Inventaire inventaire, Item item){
        this.utiliserSur(joueur);
    }
}
