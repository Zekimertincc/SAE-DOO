package fr.iut.groupe.junglequest.modele.personnages;

import fr.iut.groupe.junglequest.modele.carte.Carte;

public class StrategiePassiveLoup implements StrategieIA {

    @Override
    public void mettreAJour(Personnage ennemi, Joueur joueur, Carte carte) {
        Loup loup = (Loup) ennemi;
        double distance = joueur.getX() - loup.getX();

        // Le loup fuit ou se repose au lieu d'attaquer
        if (Math.abs(distance) < 100) {
            // Si le joueur est trop proche -> le loup fuit
            loup.fuirDe(joueur.getX());
        } else {
            // Sinon il se repose / patrouille tranquillement
            loup.seReposer();
        }
    }
}
