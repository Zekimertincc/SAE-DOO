package fr.iut.groupe.junglequest.vue;

import javafx.scene.layout.Pane;
import fr.iut.groupe.junglequest.modele.carte.Carte;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;
import fr.iut.groupe.junglequest.vue.personnages.VueJoueur;

public class VueJeu extends Pane {

    private final VueBackground vueBackground;
    private final CarteAffichable carteAffichable;
    private final Joueur joueur;
    private final VueJoueur vueJoueur;

    public VueJeu(Carte carte, Joueur joueur, VueJoueur vueJoueur, int largeurEcran, int hauteurEcran, javafx.scene.image.Image tileset) {
        this.joueur = joueur;
        this.vueJoueur = vueJoueur;

        this.setPrefSize(largeurEcran, hauteurEcran);

        this.vueBackground = new VueBackground(largeurEcran, hauteurEcran, carte.getLargeur() * 32);
        this.carteAffichable = new CarteAffichable(carte, tileset, largeurEcran, hauteurEcran);

        // Ordre d'affichage : background → carte → joueur
        this.getChildren().addAll(
                vueBackground,
                carteAffichable,
                vueJoueur.getSprite()
        );
    }

    public VueBackground getVueBackground() {
        return vueBackground;
    }

    public CarteAffichable getCarteAffichable() {
        return carteAffichable;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public VueJoueur getVueJoueur() {
        return vueJoueur;
    }
}
