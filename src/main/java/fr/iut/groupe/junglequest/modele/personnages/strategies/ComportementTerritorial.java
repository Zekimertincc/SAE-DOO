package fr.iut.groupe.junglequest.modele.personnages.strategies;

import fr.iut.groupe.junglequest.modele.carte.Carte;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;
import fr.iut.groupe.junglequest.modele.personnages.Loup;

/**
 * Stratégie territoriale pour le loup : défend une zone spécifique.
 * Implémente le pattern Strategy pour définir un comportement spécifique.
 */
public class ComportementTerritorial implements ComportementLoup {
    private final double centreZone;
    private final double rayonZone;
    private static final double VITESSE_RETOUR = 1.0;
    private static final double DISTANCE_AGRESSION = 200.0;
    
    public ComportementTerritorial(double centreZone, double rayonZone) {
        this.centreZone = centreZone;
        this.rayonZone = rayonZone;
    }

    @Override
    public void mettreAJour(Loup loup, Joueur joueur, Carte carte) {
        double distanceAuCentre = Math.abs(loup.getX() - centreZone);
        double distanceAuJoueur = Math.abs(joueur.getX() - loup.getX());

        if (distanceAuCentre > rayonZone) {
            retournerAuTerritoire(loup);
        } else if (distanceAuJoueur < DISTANCE_AGRESSION && estJoueurDansZone(joueur)) {
            attaquerIntrus(loup, joueur, carte);
        } else {
            patrouiller(loup);
        }
    }

    private boolean estJoueurDansZone(Joueur joueur) {
        return Math.abs(joueur.getX() - centreZone) <= rayonZone;
    }

    private void retournerAuTerritoire(Loup loup) {
        if (loup.getX() < centreZone) {
            loup.deplacerDroite(VITESSE_RETOUR);
        } else {
            loup.deplacerGauche(VITESSE_RETOUR);
        }
        loup.setAnimationState("run");
    }

    private void attaquerIntrus(Loup loup, Joueur joueur, Carte carte) {
        // Réutilise la logique d'attaque du comportement agressif
        new ComportementAgressif().mettreAJour(loup, joueur, carte);
    }

    private void patrouiller(Loup loup) {
        // Patrouille dans sa zone
        if (loup.getDureeAction() <= 0) {
            double nouvellePosition = centreZone + (loup.getRandom().nextDouble() * 2 - 1) * rayonZone;
            if (nouvellePosition > loup.getX()) {
                loup.deplacerDroite(0.5);
            } else {
                loup.deplacerGauche(0.5);
            }
            loup.setDureeAction(loup.getRandom().nextInt(180) + 60);
        } else {
            loup.setDureeAction(loup.getDureeAction() - 1);
        }
        loup.setAnimationState("walk");
    }

    @Override
    public String getNom() {
        return "Territorial";
    }

    @Override
    public boolean estAgressif() {
        return true;
    }
}