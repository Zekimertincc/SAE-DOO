package fr.iut.groupe.junglequest.controleur.moteur;

import fr.iut.groupe.junglequest.modele.Environnement;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;
import fr.iut.groupe.junglequest.modele.personnages.Loup;

import static fr.iut.groupe.junglequest.modele.donnees.ConstantesJeu.*;

public class ControleurCombat {
    private final Environnement env;
    private int delaiDegats = 0;

    public ControleurCombat(Environnement env) {
        this.env = env;
    }

    public void update() {
        Joueur joueur = env.getJoueur();
        Loup loup = env.getLoup();

        if (delaiDegats > 0) delaiDegats--;

        double distance = Math.abs(joueur.getX() - loup.getX());

        // collision simple
        boolean collision = distance < 20;
        if (collision && delaiDegats == 0 && !loup.estEnAttaque() && !joueur.isBouclierActif()) {
            joueur.subirDegats(loup.getDegats());
            delaiDegats = DUREE_DEGATS_JOUEUR;

            loup.attaquer(joueur.getX());
            if (joueur.getPointsDeVie() <= 0) joueur.mourir();
        }
    }
}
