package universite_paris8.iut.dagnetti.junglequest.vue.personnages;

import javafx.beans.property.DoubleProperty;
import javafx.scene.image.ImageView;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Joueur;

/**
 * Vue graphique associée au joueur.
 * Elle détient uniquement le sprite et fait le lien avec le modèle Joueur.
 */
public class VueJoueur {
    private final Joueur joueur;
    private final ImageView sprite;

    public VueJoueur(Joueur joueur, ImageView sprite) {
        this.joueur = joueur;
        this.sprite = sprite;
    }

    /**
     * Retourne le noeud graphique représentant le joueur.
     */
    public ImageView getSprite() {
        return sprite;
    }

    /**
     * Lie la position du sprite aux coordonnées du joueur en tenant compte de l'offset.
     */
    public void lierPosition(DoubleProperty offsetX, DoubleProperty offsetY) {
        sprite.xProperty().bind(joueur.xProperty().subtract(offsetX));
        sprite.yProperty().bind(joueur.yProperty().subtract(offsetY));
    }

    /**
     * Largeur du sprite utilisée pour les collisions.
     */
    public double getLargeur() {
        return sprite.getFitWidth();
    }

    /**
     * Hauteur du sprite utilisée pour les collisions.
     */
    public double getHauteur() {
        return sprite.getFitHeight();
    }
}
