package fr.iut.groupe.junglequest.vue.personnages;

import fr.iut.groupe.junglequest.modele.observateurs.Observateur;
import fr.iut.groupe.junglequest.modele.observateurs.SujetObserve;
import fr.iut.groupe.junglequest.modele.observateurs.TypeChangement;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;
import fr.iut.groupe.junglequest.vue.animation.GestionAnimation;
import javafx.scene.image.ImageView;

/**
 * Vue du joueur qui observe les changements du modèle et met à jour l'affichage en conséquence.
 */
public class VueJoueurObserver extends VueJoueur implements Observateur {
    private final GestionAnimation gestionAnimation;

    public VueJoueurObserver(Joueur joueur, ImageView sprite, GestionAnimation gestionAnimation) {
        super(joueur, sprite);
        this.gestionAnimation = gestionAnimation;
        joueur.ajouterObservateur(this);
    }

    @Override
    public void mettreAJour(SujetObserve sujet, TypeChangement type) {
        if (!(sujet instanceof Joueur)) return;
        Joueur joueur = (Joueur) sujet;

        switch (type) {
            case POSITION -> {
                getSprite().setX(joueur.getX());
                getSprite().setY(joueur.getY());
            }
            case ETAT -> {
                // Mise à jour de l'animation en fonction de l'état
                switch (joueur.getEtatCourant().getNom()) {
                    case "idle" -> gestionAnimation.animerIdle(getSprite(), 5);
                    case "marche" -> gestionAnimation.animerMarche(getSprite(), 5);
                    case "saut" -> gestionAnimation.animerSaut(getSprite(), joueur.getVitesseY());
                }
            }
            case DIRECTION -> getSprite().setScaleX(joueur.estVersGauche() ? -1 : 1);
            case POINTS_DE_VIE -> {
                // Mise à jour de la barre de vie si nécessaire
                // Cette partie peut être déléguée à une autre vue spécifique
            }
        }
    }
}