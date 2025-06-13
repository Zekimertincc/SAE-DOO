package universite_paris8.iut.dagnetti.junglequest.modele.personnages;

import javafx.scene.image.ImageView;

/**
 * Représente un ennemi de type loup.
 */
public class Loup extends Personnage {

    private final int degats;

    public Loup(ImageView sprite, double x, double y, int degats) {
        super(sprite, x, y);
        this.degats = degats;
    }

    /**
     * Dégâts infligés au joueur lors d'un contact.
     */
    public int getDegats() {
        return degats;
    }
}