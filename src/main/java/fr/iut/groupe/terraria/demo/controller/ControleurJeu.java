package fr.iut.groupe.terraria.demo.controller;

import fr.iut.groupe.terraria.demo.modele.Joueur;
import fr.iut.groupe.terraria.demo.vue.VueJeu;
import fr.iut.groupe.terraria.demo.vue.VueJoueur;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class ControleurJeu {
    private boolean gauche = false, droite = false;
    private final Joueur joueur;
    private final VueJeu vue;

    public ControleurJeu(VueJeu vue, Joueur joueur) {
        this.vue   = vue;
        this.joueur = joueur;
    }

    public void demarrer(Scene scene) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.Q  || e.getCode() == KeyCode.LEFT)  gauche = true;
            if (e.getCode() == KeyCode.D  || e.getCode() == KeyCode.RIGHT) droite = true;
            if (e.getCode() == KeyCode.SPACE)                               joueur.sauter();
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.Q  || e.getCode() == KeyCode.LEFT)  gauche = false;
            if (e.getCode() == KeyCode.D  || e.getCode() == KeyCode.RIGHT) droite = false;
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                VueJoueur vj = vue.getVueJoueur();   // sprite ve animasyon view

                if (gauche) {
                    joueur.gauche();
                    vj.updateSprite("run", false);
                } else if (droite) {
                    joueur.droite();
                    vj.updateSprite("run", true);
                } else {
                    vj.updateSprite("idle", true);   // default yön sağ
                }

                joueur.appliquerGravite();

                vj.getJoueurVue().setTranslateX(joueur.getX());
                vj.getJoueurVue().setTranslateY(joueur.getY());
            }
        }.start();
    }
}
