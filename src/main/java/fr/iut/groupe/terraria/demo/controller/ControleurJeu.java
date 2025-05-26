package fr.iut.groupe.terraria.demo.controller;

import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import fr.iut.groupe.terraria.demo.modele.ressource.Ressource;
import fr.iut.groupe.terraria.demo.vue.VueJeu;
import fr.iut.groupe.terraria.demo.vue.VueJoueur;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControleurJeu implements Initializable {

    @FXML
    private AnchorPane root;

    private boolean gauche = false, droite = false;
    private Joueur joueur;
    private VueJeu vue;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        // === Vue ===
        vue = new VueJeu();
        root.getChildren().add(vue);

        // === Modèle ===
        int[][] map = vue.getCollisionMap();
        joueur = new Joueur(100, 260, 100, null, map);

        // === Focus sur le jeu pour capter les touches ===
        vue.setFocusTraversable(true);
        vue.requestFocus();

        // === Contrôles clavier ===
        vue.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.Q || e.getCode() == KeyCode.LEFT) gauche = true;
            if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) droite = true;
            if (e.getCode() == KeyCode.SPACE) joueur.sauter();
        });

        vue.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.Q || e.getCode() == KeyCode.LEFT) gauche = false;
            if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) droite = false;
        });

        // ✅ Contrôle souris — casser ressource
        vue.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                Ressource ressourceCible = null;

                for (Ressource r : vue.getRessources()) {
                    double dx = Math.abs(joueur.getX() - r.getX());
                    double dy = Math.abs(joueur.getY() - r.getY());

                    if (dx < 40 && dy < 40) {
                        joueur.casserRessource(r);
                        vue.getChildren().remove(r.getImageView());
                        ressourceCible = r;
                        break;
                    }
                }

                if (ressourceCible != null) {
                    vue.getRessources().remove(ressourceCible);
                }
            }
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
                    vj.updateSprite("idle", true);
                }

                joueur.appliquerGravite();

                vj.getJoueurVue().setTranslateX(joueur.getX());
                vj.getJoueurVue().setTranslateY(joueur.getY());
            }
        }.start();
    }
}
