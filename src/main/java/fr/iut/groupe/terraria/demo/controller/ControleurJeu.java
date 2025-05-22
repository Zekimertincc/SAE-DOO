package fr.iut.groupe.terraria.demo.controller;

import fr.iut.groupe.terraria.demo.modele.Joueur;
import fr.iut.groupe.terraria.demo.modele.ressource.Arbre;
import fr.iut.groupe.terraria.demo.vue.VueJeu;
import fr.iut.groupe.terraria.demo.vue.VueJoueur;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControleurJeu implements Initializable {

    @FXML
    private AnchorPane root;

    private boolean gauche = false, droite = false;
    private Joueur joueur;
    private VueJeu vue;
    private Arbre arbre;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        // === Vue ===
        vue = new VueJeu();
        root.getChildren().add(vue);

        // === Modèle ===
        int[][] map = vue.getCollisionMap();
        joueur = new Joueur(100, 260, map);
        arbre = new Arbre(400,290);

        // === Focus sur le jeu pour capter les touches ===
        vue.setFocusTraversable(true);
        vue.requestFocus();

        // === Contrôles clavier ===
        vue.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.Q  || e.getCode() == KeyCode.LEFT)  gauche = true;
            if (e.getCode() == KeyCode.D  || e.getCode() == KeyCode.RIGHT) droite = true;
            if (e.getCode() == KeyCode.SPACE)                              joueur.sauter();
        });

        vue.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.Q  || e.getCode() == KeyCode.LEFT)  gauche = false;
            if (e.getCode() == KeyCode.D  || e.getCode() == KeyCode.RIGHT) droite = false;
        });

        // === Boucle du jeu ===
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                VueJoueur vj = vue.getVueJoueur();

                if (vj == null || joueur == null) return;

                if (gauche) {
                    joueur.gauche();
                    vj.updateSprite("run", false);
                } else if (droite) {
                    joueur.droite();
                    vj.updateSprite("run", true);
                } else {
                    vj.updateSprite("idle", true); // par défaut à droite
                }

                joueur.appliquerGravite();

                vj.getJoueurVue().setTranslateX(joueur.getX());
                vj.getJoueurVue().setTranslateY(joueur.getY());


                double dx = Math.abs(joueur.getX() - arbre.getX());
                double dy = Math.abs(joueur.getY() - arbre.getY());

                if (dx < 40 && dy < 40) {
                    System.out.println("🌳 Yakında ağaç var!");
                }

            }
        }.start();
    }
}
