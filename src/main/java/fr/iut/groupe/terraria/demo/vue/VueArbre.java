package fr.iut.groupe.terraria.demo.vue;

import fr.iut.groupe.terraria.demo.modele.ressource.Arbre;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class VueArbre extends Pane {
    private final Arbre arbre;

    public VueArbre(Arbre arbre) {
        this.arbre = arbre;

        Image arbreImage = new Image(getClass().getResource("/fr/iut/groupe/terraria/demo/tree.png").toExternalForm());
        ImageView imageView = new ImageView(arbreImage);
        imageView.setFitWidth(96);
        imageView.setFitHeight(64);

        imageView.setLayoutX(arbre.getX());
        imageView.setLayoutY(arbre.getY());

        this.getChildren().add(imageView);
        arbre.setImageView(imageView);
    }

    public Arbre getArbre() {
        return arbre;
    }
}
