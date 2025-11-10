package fr.iut.groupe.junglequest.modele.personnages.etats;

import fr.iut.groupe.junglequest.modele.personnages.Joueur;

/**
 * État mort du joueur (Model - MVC)
 * 
 * Architecture MVC: Pure Model - NO UI elements
 */
public class EtatMort implements EtatJoueur {
    private int compteurAnimation = 0;
    private static final int DUREE_ANIMATION_MORT = 12;
    
    @Override
    public void entrer(Joueur joueur) {
        // Implement state entry logic here
    }

    @Override
    public void sortir(Joueur joueur) {
        // Implement state exit logic here
    }

    @Override
    public void gererMouvement(Joueur joueur, boolean gauche, boolean droite) {
        // Implement movement logic here
    }

    @Override
    public void gererSaut(Joueur joueur) {
        // Implement jump logic here
    }

    @Override
    public String getNom() {
        return "mort";
    }

    @Override
    public void mettreAJour(Joueur joueur) {
        if (compteurAnimation < DUREE_ANIMATION_MORT) {
            compteurAnimation++;
        }
    }

    @Override
    public String getAnimationId() {
        return "mort";
    }

    @Override
    public boolean peutAttaquer() {
        return false;
    }

    @Override
    public boolean peutDeplacer() {
        return false;
    }
}