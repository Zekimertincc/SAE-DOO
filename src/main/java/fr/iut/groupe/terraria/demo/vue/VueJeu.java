package fr.iut.groupe.terraria.demo.vue;

import fr.iut.groupe.terraria.demo.modele.ressource.*;
import fr.iut.groupe.terraria.demo.modele.personnage.ennemi.Ennemi;
import java.util.ArrayList;
import javafx.scene.layout.Pane;
import java.util.List;

import fr.iut.groupe.terraria.demo.vue.VueEnnemi;

public class VueJeu extends Pane {
    private final VueMap vueMap;
    private final VueJoueur vueJoueur;
    private final List<Ressource> ressources;
    private final List<VueEnnemi> vueEnnemis = new ArrayList<>();

    public VueJeu(int[][] map, List<Ressource> ressources, List<Ennemi> ennemis) {
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

        for (Ennemi e : ennemis) {
            VueEnnemi ve = new VueEnnemi(e);
            vueEnnemis.add(ve);
            this.getChildren().add(ve);
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

    public List<VueEnnemi> getVueEnnemis() {
        return vueEnnemis;
    }
}
