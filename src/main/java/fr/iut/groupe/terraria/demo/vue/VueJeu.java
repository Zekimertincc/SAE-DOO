package fr.iut.groupe.terraria.demo.vue;

import javafx.scene.layout.Pane;

public class VueJeu extends Pane {
    private final VueMap vueMap;
    private final VueJoueur vueJoueur;

    public VueJeu() {
        // prefsize
        this.setPrefSize(960, 640);

        //map et joueur
        vueMap     = new VueMap();
        vueJoueur  = new VueJoueur();

        // ajouter a lecran
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
