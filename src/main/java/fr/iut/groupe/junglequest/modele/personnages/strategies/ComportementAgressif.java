package fr.iut.groupe.junglequest.modele.personnages.strategies;

import fr.iut.groupe.junglequest.modele.carte.Carte;
import fr.iut.groupe.junglequest.modele.donnees.ConstantesJeu;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;
import fr.iut.groupe.junglequest.modele.personnages.Loup;

/**
 * Stratégie agressive pour le loup : poursuit et attaque le joueur quand il est à portée.
 * Implémente le pattern Strategy pour définir un comportement spécifique.
 */
public class ComportementAgressif implements ComportementLoup {
    private static final double ZONE_DETECTION = 450.0;
    private static final double VITESSE_COURSE = 1.3;
    private static final double VITESSE_MARCHE = 0.8;

    @Override
    public void mettreAJour(Loup loup, Joueur joueur, Carte carte) {
        double distance = joueur.getX() - loup.getX();

        if (loup.estEnAttaque()) {
            poursuivreAttaque(loup);
        } else if (loup.getPausePoursuite() > 0) {
            gererPause(loup);
        } else {
            gererInteractionAvecJoueur(loup, joueur, carte, distance);
        }
    }

    private void poursuivreAttaque(Loup loup) {
        if (loup.getDirectionAttaque() >= 0) {
            if (loup.getX() < loup.getCibleAttaqueX()) {
                double step = Math.min(VITESSE_COURSE, loup.getCibleAttaqueX() - loup.getX());
                loup.deplacerDroite(step);
            } else {
                loup.arreter();
            }
        } else {
            if (loup.getX() > loup.getCibleAttaqueX()) {
                double step = Math.min(VITESSE_COURSE, loup.getX() - loup.getCibleAttaqueX());
                loup.deplacerGauche(step);
            } else {
                loup.arreter();
            }
        }
    }

    private void gererPause(Loup loup) {
        loup.gererPause();
    }

    private void gererInteractionAvecJoueur(Loup loup, Joueur joueur, Carte carte, double distance) {
        if (Math.abs(distance) <= ConstantesJeu.DISTANCE_ARRET_LOUP) {
            gererApprocheAttaque(loup, distance, joueur);
        } else if (Math.abs(distance) <= ZONE_DETECTION) {
            suivreJoueur(loup, joueur, carte);
        } else {
            patrouiller(loup);
        }
    }

    private void gererApprocheAttaque(Loup loup, double distance, Joueur joueur) {
        loup.arreter();
        if (loup.getDelaiAvantAttaque() > 0) {
            loup.setDelaiAvantAttaque(loup.getDelaiAvantAttaque() - 1);
            if (distance > 0) {
                loup.deplacerDroite(VITESSE_MARCHE);
            } else {
                loup.deplacerGauche(VITESSE_MARCHE);
            }
        } else {
            loup.attaquer(joueur.getX());
            loup.setDelaiAvantAttaque(ConstantesJeu.DELAI_AVANT_ATTAQUE_LOUP);
        }
    }

    private void suivreJoueur(Loup loup, Joueur joueur, Carte carte) {
        loup.setAnimationState("run"); // Model updates state, View will select image
        // Déplacement direct vers le joueur sans A*
        if (joueur.getX() > loup.getX()) {
            loup.deplacerDroite(VITESSE_COURSE);
        } else {
            loup.deplacerGauche(VITESSE_COURSE);
        }
        loup.setDelaiAvantAttaque(ConstantesJeu.DELAI_AVANT_ATTAQUE_LOUP);
    }

    private void patrouiller(Loup loup) {
        loup.setAnimationState("walk"); // Model updates state, View will select image
        if (loup.getDureeAction() <= 0) {
            int action = loup.getRandom().nextInt(3);
            switch (action) {
                case 0 -> loup.arreter();
                case 1 -> loup.deplacerGauche(VITESSE_MARCHE);
                case 2 -> loup.deplacerDroite(VITESSE_MARCHE);
            }
            loup.setDureeAction(loup.getRandom().nextInt(120) + 60);
        } else {
            loup.setDureeAction(loup.getDureeAction() - 1);
        }
        loup.setDelaiAvantAttaque(ConstantesJeu.DELAI_AVANT_ATTAQUE_LOUP);
    }

    @Override
    public String getNom() {
        return "Agressif";
    }

    @Override
    public boolean estAgressif() {
        return true;
    }
}