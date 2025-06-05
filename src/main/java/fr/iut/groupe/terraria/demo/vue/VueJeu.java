package fr.iut.groupe.terraria.demo.vue;

import fr.iut.groupe.terraria.demo.modele.carte.Carte;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;

import javafx.scene.layout.Pane;

public class VueJeu extends Pane {

    private final VueBackground vueBackground;
    private final CarteAffichable carteAffichable;
    private final Joueur joueur;

    public VueJeu(Carte carte, Joueur joueur, int largeurEcran, int hauteurEcran, javafx.scene.image.Image tileset) {
        this.joueur = joueur;

        this.setPrefSize(largeurEcran, hauteurEcran);

        this.vueBackground = new VueBackground(largeurEcran, hauteurEcran, carte.getLargeur() * 32);
        this.carteAffichable = new CarteAffichable(carte, tileset, largeurEcran, hauteurEcran);

        // Ordre d'affichage : background → carte → joueur
        this.getChildren().addAll(
                vueBackground,
                carteAffichable,
                joueur.getSprite()
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
}
