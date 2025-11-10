package fr.iut.groupe.junglequest.modele.personnages.etats;

import fr.iut.groupe.junglequest.modele.personnages.Joueur;

/**
 * Represents the jumping state of a character.
 * In this state, the character is in the air with upward velocity.
 * 
 * Architecture MVC: Pure Model - NO UI elements
 */
public class EtatSaut implements EtatJoueur {
    private static final String NOM = "saut";

    public EtatSaut() {
        // Constructeur public pour permettre l'instanciation directe
    }

    @Override
    public void entrer(Joueur joueur) {
        if (joueur.estAuSol()) {
            joueur.sauter(fr.iut.groupe.junglequest.modele.donnees.ConstantesJeu.IMPULSION_SAUT);
        }
    }

    @Override
    public void sortir(Joueur joueur) {
        // Nothing special when leaving jump state
    }

    @Override
    public void gererMouvement(Joueur joueur, boolean gauche, boolean droite) {
        // Allow horizontal movement while in air
        if (gauche) {
            joueur.setVitesseX(-joueur.getVitesseBase());
            joueur.setVersGauche(true);
        } else if (droite) {
            joueur.setVitesseX(joueur.getVitesseBase());
            joueur.setVersGauche(false);
        } else {
            joueur.setVitesseX(0);
        }
    }

    @Override
    public void gererSaut(Joueur joueur) {
        // Cannot jump again while in air
    }

    @Override
    public String getNom() {
        return NOM;
    }

    @Override
    public void mettreAJour(Joueur joueur) {
        // Check if landed
        if (joueur.estAuSol() && joueur.getVitesseY() >= 0) {
            joueur.changerEtat(new EtatIdle());
        }
    }

    @Override
    public String getAnimationId() {
        return "saut";
    }

    @Override
    public boolean peutAttaquer() {
        return true;  // Can attack while jumping
    }

    @Override
    public boolean peutDeplacer() {
        return true;  // Can move while jumping
    }
}