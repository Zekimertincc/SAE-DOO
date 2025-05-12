package fr.iut.groupe.terraria.demo.controller;
import fr.iut.groupe.terraria.demo.modele.*;
import fr.iut.groupe.terraria.demo.vue.VueJeu;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class ControleurJeu {
    private boolean gauche = false, droite = false, saute = false;
    private Joueur joueur;
    private VueJeu vue;

    public ControleurJeu(VueJeu vue, Joueur joueur) {
        this.vue = vue;
        this.joueur = joueur;
    }

    public void demarrer(Scene scene) {
        // Gestion clavier
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.Q || e.getCode() == KeyCode.LEFT) gauche = true;
            if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) droite = true;
            if (e.getCode() == KeyCode.SPACE) saute = true;
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.Q || e.getCode() == KeyCode.LEFT) gauche = false;
            if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) droite = false;
            if (e.getCode() == KeyCode.SPACE) saute = false;
        });

        // Boucle de jeu
        new AnimationTimer() {
            public void handle(long now) {
                if (gauche) joueur.gauche();
                if (droite) joueur.droite();
                if (saute) joueur.sauter();

                // Mise à jour de la vue
                vue.getJoueurVue().setTranslateX(joueur.getX());
                vue.getJoueurVue().setTranslateY(joueur.getY());

            }
        }.start();
    }
}

