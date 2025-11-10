package fr.iut.groupe.junglequest.modele.personnages.strategies;

import fr.iut.groupe.junglequest.modele.carte.Carte;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;
import fr.iut.groupe.junglequest.modele.personnages.Loup;

/**
 * Stratégie passive pour le loup : fuit le joueur et reste à distance.
 * Implémente le pattern Strategy pour définir un comportement spécifique.
 */
public class ComportementPassif implements ComportementLoup {
    private static final double DISTANCE_FUITE = 100.0;
    private static final double VITESSE_FUITE = 1.5;
    private static final int DUREE_REPOS = 120;

    @Override
    public void mettreAJour(Loup loup, Joueur joueur, Carte carte) {
        double distance = Math.abs(joueur.getX() - loup.getX());

        if (distance < DISTANCE_FUITE) {
            fuir(loup, joueur);
        } else {
            seReposer(loup);
        }

        // Met à jour l'état d'animation pour refléter le comportement
        loup.setAnimationState(distance < DISTANCE_FUITE ? "run" : "walk");
    }

    private void fuir(Loup loup, Joueur joueur) {
        if (loup.getX() < joueur.getX()) {
            // Fuit vers la gauche
            loup.deplacerGauche(VITESSE_FUITE);
        } else {
            // Fuit vers la droite
            loup.deplacerDroite(VITESSE_FUITE);
        }
    }

    private void seReposer(Loup loup) {
        if (loup.getDureeAction() <= 0) {
            // Décide aléatoirement de rester immobile ou de se déplacer légèrement
            if (loup.getRandom().nextInt(3) == 0) {
                loup.arreter();
            } else {
                double vitesseMarche = 0.5;
                if (loup.getRandom().nextBoolean()) {
                    loup.deplacerGauche(vitesseMarche);
                } else {
                    loup.deplacerDroite(vitesseMarche);
                }
            }
            loup.setDureeAction(loup.getRandom().nextInt(DUREE_REPOS));
        } else {
            loup.setDureeAction(loup.getDureeAction() - 1);
        }
    }

    @Override
    public String getNom() {
        return "Passif";
    }

    @Override
    public boolean estAgressif() {
        return false;
    }
}