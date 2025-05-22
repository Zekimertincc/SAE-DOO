package fr.iut.groupe.terraria.demo.vue;

import javafx.scene.layout.Pane;

public class VueJeu extends Pane {
    private final VueMap vueMap;
    private final VueJoueur vueJoueur;
    private final VueArbre vueArbre;
    public VueJeu() {
        // prefsize
        this.setPrefSize(640, 383);

        //map et joueur
        vueMap     = new VueMap();
        vueJoueur  = new VueJoueur();
        vueArbre   = new VueArbre(400,290);

        // ajouter a lecran
        this.getChildren().addAll(
                vueMap.getTilePane(),
                vueJoueur.getJoueurVue(),
                vueArbre.getVueArbre()

        );
    }



    public int[][] getCollisionMap() {
        return vueMap.getCollisionMap();
    }

    public VueJoueur getVueJoueur() {
        return vueJoueur;
    }
}
