package fr.iut.groupe.junglequest.modele.item.nourriture;

import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.item.Item;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;

public abstract class NourritureM extends Nourriture{
    public NourritureM(String nom, int vie) {
        super(nom, vie);
    }
    @Override
    public void actionRecompense(Joueur joueur, Inventaire inventaire, Item item){
        inventaire.ajouterItem(item.getNom(), 20);
    }
}
