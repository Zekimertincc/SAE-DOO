package fr.iut.groupe.junglequest.modele.personnages.etats;

import fr.iut.groupe.junglequest.modele.personnages.Joueur;

/**
 * Represents the idle state of a character.
 * In this state, the character is standing still.
 * 
 * Architecture MVC: Pure Model - NO UI elements
 */
public class EtatIdle implements EtatPersonnage, EtatJoueur {
    private static final String NOM = "idle";

    public EtatIdle() {
        // Constructeur public pour permettre l'instanciation directe
    }

    @Override
    public void entrer(Joueur joueur) {
        joueur.setVitesseX(0);
    }

    @Override
    public void sortir(Joueur joueur) {
        // Nothing special to do when leaving idle state
    }

    @Override
    public void gererMouvement(Joueur joueur, boolean gauche, boolean droite) {
        if (gauche || droite) {
            joueur.changerEtat(new EtatMarche());
        }
    }

    @Override
    public void gererSaut(Joueur joueur) {
        // No need to handle jump here as it's handled in Joueur.gererSaut
    }

    @Override
    public String getNom() {
        return NOM;
    }

    @Override
    public void mettreAJour(Joueur joueur) {
        // Check if fell off (not on ground)
        if (!joueur.estAuSol()) {
            joueur.changerEtat(new EtatSaut());
        }
    }

    @Override
    public String getAnimationId() {
        return "idle";
    }

    @Override
    public boolean peutAttaquer() {
        return true;
    }

    @Override
    public boolean peutDeplacer() {
        return true;
    }
}