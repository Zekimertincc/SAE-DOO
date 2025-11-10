package fr.iut.groupe.junglequest.modele.personnages.etats;

import fr.iut.groupe.junglequest.modele.personnages.Joueur;

/**
 * État d'attaque du joueur (Model - MVC)
 * 
 * Architecture MVC: Pure Model - NO UI elements
 */
public class EtatAttaque implements EtatJoueur {
    private int dureeAttaque;
    private static final int DUREE_MAX_ATTAQUE = 20;
    
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
        return "attaque";
    }

    public EtatAttaque() {
        this.dureeAttaque = DUREE_MAX_ATTAQUE;
    }

    @Override
    public void mettreAJour(Joueur joueur) {
        dureeAttaque--;
        if (dureeAttaque <= 0) {
            joueur.finirAttaque();
            joueur.changerEtat(new EtatIdle());
        }
    }

    @Override
    public String getAnimationId() {
        return "attaque";
    }

    @Override
    public boolean peutAttaquer() {
        return false;  // Déjà en train d'attaquer
    }

    @Override
    public boolean peutDeplacer() {
        return false;  // Pas de déplacement pendant l'attaque
    }
}