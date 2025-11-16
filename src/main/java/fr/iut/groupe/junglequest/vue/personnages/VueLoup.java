package fr.iut.groupe.junglequest.vue.personnages;

// import javafx.beans.property.DoubleProperty; // Supprimé
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import fr.iut.groupe.junglequest.modele.personnages.Loup;

// Vue graphique du loup. Gère son sprite et les images utilisées pour les différentes animations.
//

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

    // Retourne le sprite (ImageView) du loup
    public ImageView getSprite() {
        return sprite;
    }

    // Retourne l'image de marche
    public Image getWalkImage() { return walkImage; }
    // Retourne l'image de course
    public Image getRunImage() { return runImage; }
    // Retourne l'image d'attaque
    public Image getAttackImage() { return attackImage; }

    // La position est maintenant gérée directement par VueJeuController.
    //
    
    // Retourne la largeur du sprite
    public double getLargeur() { return sprite.getFitWidth(); }
    // Retourne la hauteur du sprite
    public double getHauteur() { return sprite.getFitHeight(); }
    
    // Met à jour l'animation du loup en fonction de l'état du modèle.
    // Lit l'animationState du Loup et sélectionne l'image appropriée.
    public void mettreAJourAnimation() {
        String state = loup.getAnimationState();
        Image newImage = null;
        
        switch (state) {
            case "walk" -> newImage = walkImage;
            case "run" -> newImage = runImage;
            case "attack" -> newImage = attackImage;
            default -> newImage = walkImage; // Par défaut : marche
        }
        
        if (newImage != null && sprite.getImage() != newImage) {
            sprite.setImage(newImage);
        }
        
        // Met à jour la direction en fonction de l'orientation du modèle
        sprite.setScaleX(loup.estVersGauche() ? -1 : 1);
    }
}
