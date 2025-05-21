package fr.iut.groupe.terraria.demo.vue;

import javafx.scene.layout.Pane;

public class VueJeu extends Pane {
    private final VueMap vueMap;
    private final VueJoueur vueJoueur;

    public VueJeu() {
        // set preferred size
        this.setPrefSize(800, 600); // önceki 640x383 yerine daha geniş
        this.setStyle("-fx-background-color: #000000;");

        // initialize map and player view
        vueMap    = new VueMap();
        vueJoueur = new VueJoueur();

        // add both to scene
        this.getChildren().addAll(
                vueMap.getTilePane(),
                vueJoueur.getJoueurVue()
        );
    }

    public int[][] getCollisionMap() {
        return vueMap.getCollisionMap();
    }

    public VueJoueur getVueJoueur() {
        return vueJoueur;
    }
}
