package fr.iut.groupe.terraria.demo;

import fr.iut.groupe.terraria.demo.controller.ControleurJeu;
import fr.iut.groupe.terraria.demo.modele.Joueur;
import fr.iut.groupe.terraria.demo.vue.VueJeu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // === VIEW ===
        VueJeu vue = new VueJeu();          // harita + sprite view’ları oluşturur

        // === MODEL ===
        int[][] map = vue.getCollisionMap(); // VueMap’ten gelen collision matrisi
        Joueur joueur = new Joueur(100, 260, map);

        // === CONTROLLER ===
        ControleurJeu controleur = new ControleurJeu(vue, joueur);

        // === SCENE & STAGE ===
        Scene scene = new Scene(vue);
        vue.requestFocus();                 // klavye input’u alabilsin
        controleur.demarrer(scene);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Jeu MVC – Déplacement + Saut");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
