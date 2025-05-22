package fr.iut.groupe.terraria.demo.vue;

import fr.iut.groupe.terraria.demo.modele.ressource.Arbre;
import fr.iut.groupe.terraria.demo.modele.ressource.Ressource;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class VueJeu extends Pane {
    private final VueMap vueMap;
    private final VueJoueur vueJoueur;
    private final List<Ressource> ressources;

    public VueJeu() {
        this.setPrefSize(640, 383);

        vueMap = new VueMap();
        vueJoueur = new VueJoueur();
        ressources = new ArrayList<>();

        // === Kaynakları listeye ekle ===
        ressources.add(new Arbre(300, 200));
        ressources.add(new Arbre(500, 220));
        ressources.add(new Arbre(150, 250));

        // === Kaynakları sahneye çiz ===
        for (Ressource r : ressources) {
            ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/tree.png")));
            img.setFitWidth(96);
            img.setFitHeight(64);
            img.setLayoutX(r.getX());
            img.setLayoutY(r.getY());
            this.getChildren().add(img);
        }

        // === Ekrana diğer şeyleri ekle ===
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

    public List<Ressource> getRessources() {
        return ressources;
    }
}
