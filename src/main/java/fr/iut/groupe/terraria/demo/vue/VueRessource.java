package fr.iut.groupe.terraria.demo.vue;

import fr.iut.groupe.terraria.demo.modele.ressource.Ressource;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Vue générique pour afficher une ressource.
 */
public abstract class VueRessource<T extends Ressource> extends Pane {
    protected final T ressource;

    protected VueRessource(T ressource, String imagePath, double width, double height) {
        this.ressource = ressource;

        Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView view = new ImageView(image);
        view.setFitWidth(width);
        view.setFitHeight(height);
        view.setLayoutX(ressource.getX());
        view.setLayoutY(ressource.getY());
        getChildren().add(view);

        ressource.setImageView(view);
        ressource.setVueNode(this);
    }

    public T getRessource() {
        return ressource;
    }
}
