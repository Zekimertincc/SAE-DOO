package fr.iut.groupe.terraria.demo.controller;

import fr.iut.groupe.terraria.demo.modele.monde.carte.Carte;
import fr.iut.groupe.terraria.demo.modele.monde.carte.ChargeurCarte;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import fr.iut.groupe.terraria.demo.modele.ressource.Ressource;
import fr.iut.groupe.terraria.demo.vue.VueJeu;
import fr.iut.groupe.terraria.demo.vue.VueJoueur;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControleurJeu implements Initializable {

    @FXML
    private AnchorPane root;

    private boolean gauche = false, droite = false;
    private Joueur joueur;
    private VueJeu vue;
    private InventaireController inventaireController;
    private Carte carte;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        // === Chargement de la carte ===
        carte = ChargeurCarte.charger("/fr/iut/groupe/terraria/demo/terrain_jungle.csv");
        if (carte != null) {
            System.out.println("Carte chargée avec succès !");
            System.out.println("Dimensions : " + carte.getLargeur() + " x " + carte.getHauteur());
        } else {
            System.out.println("Erreur de chargement de la carte !");
        }

        // === Vue ===
        vue = new VueJeu();
        vue.setCarte(carte);  // On passe la carte à VueJeu
        root.getChildren().add(vue);  // Correction : pas getScroll

        // === Modèle ===
        int[][] map = vue.getCollisionMap();
        joueur = new Joueur(100, 260, 100, null, map);

        // === Inventaire GUI ===
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/iut/groupe/terraria/demo/vue/Inventaire.fxml"));
            AnchorPane inventairePane = loader.load();

            inventaireController = loader.getController();
            inventaireController.setInventaire(joueur.getInventaire());

            inventairePane.setLayoutX(10);
            inventairePane.setLayoutY(10);
            root.getChildren().add(inventairePane);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // === Focus ===
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

        // === Clic gauche → casser ressource + update inventaire ===
        vue.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                Ressource cible = vue.getRessources().stream()
                        .filter(r -> Math.abs(joueur.getX() - r.getX()) < 100 && Math.abs(joueur.getY() - r.getY()) < 100)
                        .findFirst().orElse(null);

                if (cible != null) {
                    joueur.casserRessource(cible);
                    vue.getChildren().remove(cible.getImageView());
                    vue.getRessources().remove(cible);
                    inventaireController.updateAffichage();
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

    public void refreshVue() {
        root.getChildren().clear();
        vue = new VueJeu();
        vue.setCarte(carte);  // Recharge la carte
        root.getChildren().add(vue);  // Correction ici aussi
        vue.requestFocus();
    }
}
