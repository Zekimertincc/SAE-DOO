package fr.iut.groupe.junglequest.vue.observateur;

import fr.iut.groupe.junglequest.modele.observateurs.Observateur;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;

import fr.iut.groupe.junglequest.modele.observateurs.SujetObserve;
import fr.iut.groupe.junglequest.modele.observateurs.TypeChangement;

public class VueJoueurObservateur implements Observateur {

    private final Joueur joueur;

    public VueJoueurObservateur(Joueur joueur) {
        this.joueur = joueur;
    }

    @Override
    public void mettreAJour(SujetObserve sujet, TypeChangement type) {
        System.out.println("💥 Mise à jour de la vie du joueur : " + joueur.getPointsDeVie());
        // ➕ Plus tard, tu pourras ici mettre à jour une barre de vie JavaFX, par ex :
        // barreVie.setProgress((double) joueur.getPointsDeVie() / joueur.getVieMax());
    }
}
