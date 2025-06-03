package fr.iut.groupe.terraria.demo.vue;

import fr.iut.groupe.terraria.demo.modele.ressource.CanneSucre;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class VueCanneSucre extends Pane {
    private final CanneSucre canne;

    public VueCanneSucre(CanneSucre canne) {
        this.canne = canne;

        Image canneImage = new Image(getClass().getResource("/fr/iut/groupe/terraria/demo/CanneSucre.png").toExternalForm());
        ImageView imageView = new ImageView(canneImage);
        imageView.setFitWidth(96);
        imageView.setFitHeight(64);

        imageView.setLayoutX(canne.getX());
        imageView.setLayoutY(canne.getY());

        this.getChildren().add(imageView);
        canne.setImageView(imageView);
    }

    public CanneSucre getCanneSucre() {
        return canne;
    }
}