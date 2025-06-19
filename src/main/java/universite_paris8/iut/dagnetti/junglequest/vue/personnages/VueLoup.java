package universite_paris8.iut.dagnetti.junglequest.vue.personnages;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Loup;

/**
 * Vue graphique du loup. Gère son sprite et les images utilisées pour les différentes animations.
 */
public class VueLoup {
    private final Loup loup;
    private final ImageView sprite;
    private final Image walkImage;
    private final Image runImage;
    private final Image attackImage;

    public VueLoup(Loup loup, ImageView sprite, Image walkImage, Image runImage, Image attackImage) {
        this.loup = loup;
        this.sprite = sprite;
        this.walkImage = walkImage;
        this.runImage = runImage;
        this.attackImage = attackImage;
    }

    public ImageView getSprite() {
        return sprite;
    }

    public Image getWalkImage() { return walkImage; }
    public Image getRunImage() { return runImage; }
    public Image getAttackImage() { return attackImage; }

    public void synchroniserPosition(double offsetX, double offsetY) {
        sprite.setX(loup.getX() - offsetX);
        sprite.setY(loup.getY() - offsetY);
    }

    public double getLargeur() { return sprite.getFitWidth(); }
    public double getHauteur() { return sprite.getFitHeight(); }
}
