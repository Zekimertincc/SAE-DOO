package fr.iut.groupe.junglequest.controleur.commandes;

import fr.iut.groupe.junglequest.modele.personnages.Joueur;
import fr.iut.groupe.junglequest.modele.personnages.Loup;
import fr.iut.groupe.junglequest.modele.donnees.ConstantesJeu;

/**
 * Commande pour gérer l'attaque du joueur.
 */
public class CommandeAttaquerJoueur implements Commande {
    private final Joueur joueur;
    private final Loup loup;
    private final double distance;

    public CommandeAttaquerJoueur(Joueur joueur, Loup loup, double distance) {
        this.joueur = joueur;
        this.loup = loup;
        this.distance = distance;
    }

    @Override
    public void executer() {
        if (!joueur.estEnAttaque()) {
            joueur.attaquer();
            if (distance <= ConstantesJeu.PORTEE_ATTAQUE_JOUEUR) {
                loup.subirDegats(ConstantesJeu.DEGATS_JOUEUR_LOUP);
            }
        } else {
            if (distance <= ConstantesJeu.PORTEE_ATTAQUE_JOUEUR) {
                loup.subirDegats(ConstantesJeu.DEGATS_COMBO_SUPPLEMENTAIRES);
            }
        }
        
        if (joueur.isBouclierActif()) {
            joueur.desactiverBouclier();
        }
    }
}