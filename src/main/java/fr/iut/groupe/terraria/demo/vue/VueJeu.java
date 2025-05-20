package fr.iut.groupe.terraria.demo.vue;

import javafx.scene.layout.Pane;

public class VueJeu extends Pane {
    private final VueMap vueMap;
    private final VueJoueur vueJoueur;

    public VueJeu() {
        // Orijinal prefSize
        this.setPrefSize(960, 640);

        // Harita ve karakter görünümleri
        vueMap     = new VueMap();
        vueJoueur  = new VueJoueur();

        // Ekrana ekle
        this.getChildren().addAll(
                vueMap.getTilePane(),
                vueJoueur.getJoueurVue()
        );
    }

    /* ---- Proxy getter’lar (Controller ihtiyaç duyuyor) ---- */

    public int[][] getCollisionMap() {           // harita matrisi
        return vueMap.getCollisionMap();
    }

    public VueJoueur getVueJoueur() {            // VueJoueur nesnesi
        return vueJoueur;
    }
}
