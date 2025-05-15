package fr.iut.groupe.terraria.demo.controller;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import fr.iut.groupe.terraria.demo.modele.Joueur;
import fr.iut.groupe.terraria.demo.vue.VueJeu;

public class ControleurJeu {

    private final Joueur joueur;
    private final VueJeu vue;

    private boolean gauche = false;
    private boolean droite = false;

    public ControleurJeu(VueJeu vue, Joueur joueur) {
        this.vue = vue;
        this.joueur = joueur;
    }

    public void demarrer(Scene scene) {
        scene.setOnKeyPressed(e -> {
            KeyCode code = e.getCode();
            if (code == KeyCode.Q || code == KeyCode.LEFT) gauche = true;
            if (code == KeyCode.D || code == KeyCode.RIGHT) droite = true;
            if (code == KeyCode.SPACE) joueur.sauter();
        });

        scene.setOnKeyReleased(e -> {
            KeyCode code = e.getCode();
            if (code == KeyCode.Q || code == KeyCode.LEFT) gauche = false;
            if (code == KeyCode.D || code == KeyCode.RIGHT) droite = false;
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gauche) joueur.allerAGauche();
                if (droite) joueur.allerADroite();
                joueur.appliquerGravite();

                vue.getJoueurVue().setTranslateX(joueur.getX());
                vue.getJoueurVue().setTranslateY(joueur.getY());
            }
        }.start();
    }
}
