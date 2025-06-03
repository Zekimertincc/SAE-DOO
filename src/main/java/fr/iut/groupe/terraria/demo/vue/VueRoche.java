package fr.iut.groupe.terraria.demo.vue;

import fr.iut.groupe.terraria.demo.modele.ressource.Roche;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class VueRoche extends Pane {
    private final Roche roche;

    public VueRoche(Roche roche) {
        this.roche = roche;

        Image rocheImage = new Image(getClass().getResource("/fr/iut/groupe/terraria/demo/roche.png").toExternalForm());
        ImageView imageView = new ImageView(rocheImage);
        imageView.setFitWidth(64);
        imageView.setFitHeight(64);

        imageView.setLayoutX(roche.getX());
        imageView.setLayoutY(roche.getY());

        this.getChildren().add(imageView);
        roche.setImageView(imageView);
        roche.setVueNode(this); // 🔥 Vue node olarak kaydediliyor
    }

    public Roche getRoche() {
        return roche;
    }
}
