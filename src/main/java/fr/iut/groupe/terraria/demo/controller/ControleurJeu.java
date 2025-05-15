package fr.iut.groupe.terraria.demo.controller;

import fr.iut.groupe.terraria.demo.modele.Joueur;
import fr.iut.groupe.terraria.demo.vue.VueJeu;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class ControleurJeu {
    private boolean gauche = false, droite = false;
    private Joueur joueur;
    private VueJeu vue;

    public ControleurJeu(VueJeu vue, Joueur joueur) {
        this.vue = vue;
        this.joueur = joueur;
    }

    public void demarrer(Scene scene) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.Q || e.getCode() == KeyCode.LEFT) gauche = true;
            if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) droite = true;
            if (e.getCode() == KeyCode.SPACE) joueur.sauter();
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.Q || e.getCode() == KeyCode.LEFT) gauche = false;
            if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) droite = false;
        });

        new AnimationTimer() {
            public void handle(long now) {
                int[][] map = vue.getCollisionMap();
                int mapWidthPx = map[0].length * 32;

                if (gauche) joueur.gauche(map);
                if (droite) joueur.droite(map, mapWidthPx);
                joueur.appliquerGravite(map);

                vue.getJoueurVue().setTranslateX(joueur.getX());
                vue.getJoueurVue().setTranslateY(joueur.getY());
            }
        }.start();
    }
}
