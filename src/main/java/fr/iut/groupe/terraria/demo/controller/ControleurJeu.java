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

    private double hauteurEcran = 600;
    private double hauteurJoueur = 40;

    public ControleurJeu(VueJeu vue, Joueur joueur) {
        this.vue = vue;
        this.joueur = joueur;
    }

    public void demarrer(Scene scene) {
        // Gestion clavier
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.Q || e.getCode() == KeyCode.LEFT) gauche = true;
            if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) droite = true;
            if (e.getCode() == KeyCode.SPACE) joueur.sauter();
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.Q || e.getCode() == KeyCode.LEFT) gauche = false;
            if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) droite = false;
        });

        // Boucle de jeu
        new AnimationTimer() {
            public void handle(long now) {
                if (gauche) joueur.gauche();
                if (droite) joueur.droite();
                joueur.appliquerGravite();

                // Mise à jour de la vue avec inversion de l’axe Y
                vue.getJoueurVue().setTranslateX(joueur.getX());
                vue.getJoueurVue().setTranslateY(hauteurEcran - joueur.getY() - hauteurJoueur);
            }
        }.start();
    }
}

