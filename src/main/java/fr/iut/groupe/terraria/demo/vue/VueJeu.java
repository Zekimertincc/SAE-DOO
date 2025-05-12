package fr.iut.groupe.terraria.demo.vue;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class VueJeu extends Pane {
    private Rectangle playerView;
    private Rectangle ground;

    public VueJeu() {
        setPrefSize(800, 600);

        // Sol
        ground = new Rectangle(800, 100);
        ground.setTranslateY(500); // Position Y du sol
        ground.setFill(Color.DARKGREEN);

        // Joueur
        playerView = new Rectangle(40, 40, Color.RED);

        getChildren().addAll(ground, playerView);
    }

    public Rectangle getPlayerView() {
        return playerView;
    }
}


