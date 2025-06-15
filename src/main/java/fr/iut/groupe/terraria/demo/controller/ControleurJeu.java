package fr.iut.groupe.terraria.demo.controller;

import fr.iut.groupe.terraria.demo.modele.item.equipement.Equipement;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import fr.iut.groupe.terraria.demo.modele.ressource.Ressource;
import fr.iut.groupe.terraria.demo.vue.VueJeu;
import fr.iut.groupe.terraria.demo.vue.VueJoueur;
import fr.iut.groupe.terraria.demo.modele.Monde;
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
    private Monde monde;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        // l'initialisation du jeu se fait dans initJeu()
    }

    public void initJeu(Monde monde, Joueur joueur) {
        this.monde = monde;
        this.joueur = joueur;

        vue = new VueJeu(monde.getMap(), monde.getRessources());
        root.getChildren().add(vue);

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

        vue.setFocusTraversable(true);
        vue.requestFocus();

        vue.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.Q || e.getCode() == KeyCode.LEFT) gauche = true;
            if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) droite = true;
            if (e.getCode() == KeyCode.SPACE) joueur.sauter();
        });

        vue.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.Q || e.getCode() == KeyCode.LEFT) gauche = false;
            if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) droite = false;
        });

        vue.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                Ressource cible = monde.getRessources().stream()
                        .filter(r -> Math.abs(joueur.getX() - r.getX()) < 100 && Math.abs(joueur.getY() - r.getY()) < 100)
                        .findFirst().orElse(null);

                if (cible != null) {
                    int vieAvant = cible.getVie();
                    Equipement outil = joueur.getEquipementActuel();

                    if (outil != null) {
                        joueur.utiliserEquipementSur(cible);
                    } else {
                        cible.subirDegats(1);
                    }

                    if (cible.getVie() <= 0) {
                        vue.getChildren().remove(cible.getVueNode());
                        monde.getRessources().remove(cible);
                        joueur.casserRessource(cible);
                    }

                    inventaireController.updateAffichage();
                }
            }
        });

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
        vue = new VueJeu(monde.getMap(), monde.getRessources());
        root.getChildren().add(vue);
        vue.requestFocus();
    }
}
