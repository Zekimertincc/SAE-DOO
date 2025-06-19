package universite_paris8.iut.dagnetti.junglequest.vue.personnages;

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
     * Met à jour la position du sprite à partir du modèle.
     */
    public void synchroniserPosition(double offsetX, double offsetY) {
        sprite.setX(joueur.getX() - offsetX);
        sprite.setY(joueur.getY() - offsetY);
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
