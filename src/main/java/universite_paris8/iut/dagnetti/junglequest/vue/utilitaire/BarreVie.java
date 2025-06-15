package universite_paris8.iut.dagnetti.junglequest.vue.utilitaire;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Barre de vie personnalisée utilisant un simple rectangle coloré.
 * L'affichage passe progressivement du vert au rouge lorsque la vie
 * diminue et la largeur de la barre s'adapte au ratio de points de vie.
 */
public class BarreVie extends Pane {

    private final Rectangle fond;
    private final Rectangle vie;
    private final double largeur;
    private final double hauteur;

    public BarreVie(double largeur, double hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;

        fond = new Rectangle(largeur, hauteur);
        fond.setFill(Color.WHITE);
        fond.setStroke(Color.BLACK);
        fond.setStrokeWidth(1);

        vie = new Rectangle(largeur, hauteur);
        vie.setFill(Color.GREEN);

        this.getChildren().addAll(fond, vie);
        this.setPrefSize(largeur, hauteur);
    }

    /**
     * Met à jour la largeur et la couleur de la barre de vie.
     *
     * @param ratio valeur comprise entre 0 et 1 correspondant au
     *              pourcentage de points de vie restants
     */
    public void mettreAJour(double ratio) {
        ratio = Math.max(0, Math.min(1, ratio));
        vie.setWidth(largeur * ratio);
        Color couleur = Color.GREEN.interpolate(Color.RED, 1 - ratio);
        vie.setFill(couleur);
    }
}
