package fr.iut.groupe.terraria.demo.vue;

import fr.iut.groupe.terraria.demo.modele.ressource.Arbre;
import fr.iut.groupe.terraria.demo.modele.ressource.Roche;
import fr.iut.groupe.terraria.demo.modele.ressource.Ressource;
import fr.iut.groupe.terraria.demo.modele.ressource.CanneSucre;
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

        // Ajout des arbres
        ressources.add(new Arbre(300, 200));
        ressources.add(new Arbre(500, 220));
        ressources.add(new Arbre(150, 250));

        // Ajout des rochers
        ressources.add(new Roche(400, 300));
        ressources.add(new Roche(600, 350));

        ressources.add(new CanneSucre(200, 250));
        ressources.add(new CanneSucre(500, 280));


        // Affichage des ressources
        for (Ressource r : ressources) {
            Image img;
            if (r instanceof Arbre) {
                img = new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/tree.png"));
            } else if (r instanceof Roche) {
                img = new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/roche.png"));
            } else if (r instanceof CanneSucre) {
                img = new Image(getClass().getResourceAsStream("/fr/iut/groupe/terraria/demo/CanneSucre.png"));  // change k3.png si besoin
            } else {
                continue;

        }

            ImageView imgView = new ImageView(img);
            if (r instanceof Roche) {
                imgView.setFitWidth(147);
                imgView.setFitHeight(135);
            } else {
                imgView.setFitWidth(96);
                imgView.setFitHeight(64);
            }

            imgView.setLayoutX(r.getX());
            imgView.setLayoutY(r.getY());
            r.setImageView(imgView);
            this.getChildren().add(imgView);
        }

        // Ajout map et joueur
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
