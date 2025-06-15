package fr.iut.groupe.terraria.demo.vue;

import fr.iut.groupe.terraria.demo.modele.ressource.*;
import javafx.scene.layout.Pane;
import java.util.List;

public class VueJeu extends Pane {
    private final VueMap vueMap;
    private final VueJoueur vueJoueur;
    private final List<Ressource> ressources;

    public VueJeu(int[][] map, List<Ressource> ressources) {
        this.setPrefSize(640, 383);
        vueMap = new VueMap(map);
        vueJoueur = new VueJoueur();
        this.ressources = ressources;

        for (Ressource r : ressources) {
            if (r instanceof Arbre) {
                this.getChildren().add(new VueArbre((Arbre) r));
            } else if (r instanceof Roche) {
                this.getChildren().add(new VueRoche((Roche) r));
            } else if (r instanceof CanneSucre) {
                this.getChildren().add(new VueCanneSucre((CanneSucre) r));
            }
        }

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
