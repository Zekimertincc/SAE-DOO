package fr.iut.groupe.terraria.demo.vue;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class VueJeu extends Pane {
    private Rectangle joueurVue;
    private Rectangle sol;

    public VueJeu() {
        setPrefSize(800, 600);

        sol = new Rectangle(800, 50);
        sol.setTranslateY(550); // hauteur de ta fenêtre est 600 et sol fait 50
        sol.setFill(Color.DARKGREEN);

        joueurVue = new Rectangle(40, 40, Color.RED);
        getChildren().addAll(sol, joueurVue);
    }

    public Rectangle getJoueurVue() {
        return joueurVue;
    }
}




