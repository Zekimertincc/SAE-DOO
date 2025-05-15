package fr.iut.groupe.terraria.demo.vue;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class VueJeu extends Pane {
    private Rectangle joueurVue;
    private Rectangle sol;

    public VueJeu() {
        setPrefSize(1024, 640);

        sol = new Rectangle(1024, 50);
        sol.setTranslateY(590);
        sol.setFill(Color.DARKGREEN);

        joueurVue = new Rectangle(40, 40, Color.RED);
        getChildren().addAll(sol, joueurVue);
    }

    public Rectangle getJoueurVue() {
        return joueurVue;
    }
}
