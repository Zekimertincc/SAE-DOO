package fr.iut.groupe.junglequest.vue.personnages;

import javafx.beans.property.DoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import fr.iut.groupe.junglequest.modele.personnages.Loup;

/**
 * Vue graphique du loup. Gère son sprite et les images utilisées pour les différentes animations.
 * 
 * Architecture MVC:
 * - View class handles rendering based on Model state
 * - Reads animationState from Loup model
 * - Selects appropriate image based on state
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

    /**
     * Lie la position du sprite aux coordonnées du loup en tenant compte de l'offset.
     */
    public void lierPosition(DoubleProperty offsetX, DoubleProperty offsetY) {
        sprite.xProperty().bind(loup.xProperty().subtract(offsetX));
        sprite.yProperty().bind(loup.yProperty().subtract(offsetY));
    }

    public double getLargeur() { return sprite.getFitWidth(); }
    public double getHauteur() { return sprite.getFitHeight(); }
    
    /**
     * Met à jour l'animation du loup en fonction de l'état du modèle.
     * Lit l'animationState du Loup et sélectionne l'image appropriée.
     */
    public void mettreAJourAnimation() {
        String state = loup.getAnimationState();
        Image newImage = null;
        
        switch (state) {
            case "walk" -> newImage = walkImage;
            case "run" -> newImage = runImage;
            case "attack" -> newImage = attackImage;
            default -> newImage = walkImage; // Default to walk
        }
        
        if (newImage != null && sprite.getImage() != newImage) {
            sprite.setImage(newImage);
        }
        
        // Update direction based on facing direction
        sprite.setScaleX(loup.estVersGauche() ? -1 : 1);
    }
}
