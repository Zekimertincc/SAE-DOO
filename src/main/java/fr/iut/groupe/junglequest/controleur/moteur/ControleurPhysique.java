package fr.iut.groupe.junglequest.controleur.moteur;

import fr.iut.groupe.junglequest.modele.Environnement;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;
import fr.iut.groupe.junglequest.modele.personnages.Loup;
import fr.iut.groupe.junglequest.modele.carte.Carte;

public class ControleurPhysique {
    private final Environnement env;

    public ControleurPhysique(Environnement env) {
        this.env = env;
    }

    public void update() {
        Joueur joueur = env.getJoueur();
        Loup loup = env.getLoup();
        Carte carte = env.getCarte();

        env.getMoteurPhysique().mettreAJourPhysique(joueur, carte, joueur.getLargeur(), joueur.getHauteur());
        env.getMoteurPhysique().mettreAJourPhysique(loup, carte, loup.getLargeur(), loup.getHauteur());
    }
}
