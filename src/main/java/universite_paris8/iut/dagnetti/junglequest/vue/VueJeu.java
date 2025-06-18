package universite_paris8.iut.dagnetti.junglequest.vue;

import javafx.scene.layout.Pane;
import universite_paris8.iut.dagnetti.junglequest.modele.carte.Carte;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Joueur;
import universite_paris8.iut.dagnetti.junglequest.modele.donnees.ConstantesJeu;

public class VueJeu extends Pane {

    private final VueBackground vueBackground;
    private final CarteAffichable carteAffichable;
    private final Joueur joueur;

    public VueJeu(Carte carte, Joueur joueur, int largeurEcran, int hauteurEcran, javafx.scene.image.Image tileset) {
        this.joueur = joueur;

        this.setPrefSize(largeurEcran, hauteurEcran);

        this.vueBackground = new VueBackground(largeurEcran, hauteurEcran, carte.getLargeur() * ConstantesJeu.TAILLE_TUILE);
        this.carteAffichable = new CarteAffichable(carte, tileset, largeurEcran, hauteurEcran, ConstantesJeu.FACTEUR_ZOOM_CARTE);

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
