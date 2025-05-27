package fr.iut.groupe.terraria.demo.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class VueRoche extends Pane {
    private ImageView imageView;

    public VueRoche() {
        // Charger l'image de la roche (mets le chemin correct dans resources)
        Image rocheImage = new Image(getClass().getResource("/fr/iut/groupe/terraria/demo/roche.png").toExternalForm());
        imageView = new ImageView(rocheImage);

        // Configurer la taille
        imageView.setFitWidth(147);
        imageView.setFitHeight(135);

        // Ajouter à la vue
        this.getChildren().add(imageView);
    }
}
