package fr.iut.groupe.junglequest.controleur.commandes;

import fr.iut.groupe.junglequest.vue.animation.GestionAnimation;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;
import javafx.scene.image.ImageView;

/**
 * Commande pour gérer l'animation du joueur en fonction de son état.
 */
public class CommandeAnimationJoueur implements Commande {
    private final Joueur joueur;
    private final GestionAnimation animation;
    private final ImageView sprite;
    private final boolean toucheAccroupi;
    private final int delaiDegats;
    private final int framesAtterrissageRestants;
    private final int compteurAttaque;
    private final int frameMort;
    private final boolean joueurMort;
    private static final int DELAI_FRAME = 5;

    public CommandeAnimationJoueur(Joueur joueur, GestionAnimation animation, ImageView sprite,
                                 boolean toucheAccroupi, int delaiDegats, int framesAtterrissageRestants,
                                 int compteurAttaque, int frameMort, boolean joueurMort) {
        this.joueur = joueur;
        this.animation = animation;
        this.sprite = sprite;
        this.toucheAccroupi = toucheAccroupi;
        this.delaiDegats = delaiDegats;
        this.framesAtterrissageRestants = framesAtterrissageRestants;
        this.compteurAttaque = compteurAttaque;
        this.frameMort = frameMort;
        this.joueurMort = joueurMort;
    }

    @Override
    public void executer() {
        if (joueurMort) {
            animation.animerMort(sprite, frameMort);
        } else if (delaiDegats > 0) {
            animation.animerDegats(sprite);
        } else if (joueur.estEnAttaque()) {
            animation.animerAttaque(sprite, Math.max(1, DELAI_FRAME - 2), joueur::finAttaque);
        } else if (!joueur.estAuSol()) {
            animation.animerSaut(sprite, joueur.getVitesseY());
        } else if (framesAtterrissageRestants > 0) {
            animation.animerAtterrissage(sprite, animation.getNbFramesAtterrissage() - framesAtterrissageRestants);
        } else if (toucheAccroupi) {
            animation.animerAccroupi(sprite);
        } else if (joueur.isBouclierActif()) {
            animation.animerBouclier(sprite);
        } else if (joueur.getVitesseX() != 0) {
            animation.animerMarche(sprite, DELAI_FRAME);
        } else {
            animation.animerIdle(sprite, DELAI_FRAME);
        }

        sprite.setScaleX(joueur.estVersGauche() ? -1 : 1);
    }
}