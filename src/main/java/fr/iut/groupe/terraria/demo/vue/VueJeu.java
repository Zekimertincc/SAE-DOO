package fr.iut.groupe.terraria.demo.vue;

import fr.iut.groupe.terraria.demo.modele.ressource.*;
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

        ressources.add(new Arbre(300, 200));
        ressources.add(new Arbre(500, 220));
        ressources.add(new Arbre(150, 250));
        ressources.add(new Roche(400, 300));
        ressources.add(new Roche(600, 350));
        ressources.add(new CanneSucre(200, 250));
        ressources.add(new CanneSucre(500, 280));

        for (Ressource r : ressources) {
            if (r instanceof Arbre) {
                VueArbre vueArbre = new VueArbre((Arbre) r);
                this.getChildren().add(vueArbre);
            } else if (r instanceof Roche) {
                VueRoche vueRoche = new VueRoche((Roche) r);
                this.getChildren().add(vueRoche);
            } else if (r instanceof CanneSucre) {
                VueCanneSucre vueCanne = new VueCanneSucre((CanneSucre) r);
                this.getChildren().add(vueCanne);
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
