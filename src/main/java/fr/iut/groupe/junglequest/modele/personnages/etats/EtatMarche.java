package fr.iut.groupe.junglequest.modele.personnages.etats;

import fr.iut.groupe.junglequest.modele.personnages.Joueur;

/**
 * Represents the walking state of a character.
 * In this state, the character is moving horizontally.
 * 
 * Architecture MVC: Pure Model - NO UI elements
 */
public class EtatMarche implements EtatJoueur {
    private static final String NOM = "marche";

    public EtatMarche() {
        // Constructeur public pour permettre l'instanciation directe
    }

    @Override
    public void entrer(Joueur joueur) {
        // Movement is set by gererMouvement, not here
    }

    @Override
    public void sortir(Joueur joueur) {
        joueur.setVitesseX(0);
    }

    @Override
    public void gererMouvement(Joueur joueur, boolean gauche, boolean droite) {
        if (!gauche && !droite) {
            joueur.changerEtat(new EtatIdle());
        }
        // Movement is handled in Joueur.gererMouvement, no need to set velocity here
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
        // Check if stopped moving
        if (joueur.getVitesseX() == 0 && joueur.estAuSol()) {
            joueur.changerEtat(new EtatIdle());
        }
        // Check if in air (jumped or fell)
        if (!joueur.estAuSol()) {
            joueur.changerEtat(new EtatSaut());
        }
    }

    @Override
    public String getAnimationId() {
        return "marche";
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