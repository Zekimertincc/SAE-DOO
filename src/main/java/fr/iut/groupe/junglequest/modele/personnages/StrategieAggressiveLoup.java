package fr.iut.groupe.junglequest.modele.personnages;

import fr.iut.groupe.junglequest.modele.carte.Carte;

public class StrategieAggressiveLoup implements StrategieIA {

    @Override
    public void mettreAJour(Personnage ennemi, Joueur joueur, Carte carte) {
        Loup loup = (Loup) ennemi;
        double distance = joueur.getX() - loup.getX();

        if (loup.estEnAttaque()) {
            loup.poursuivreAttaque();
        } else if (loup.getPausePoursuite() > 0) {
            loup.gererPause();
        } else {
            loup.gererInteractionAvecJoueur(distance, joueur, carte);
        }
    }
}
