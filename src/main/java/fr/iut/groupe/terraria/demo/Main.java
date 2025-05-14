

package fr.iut.groupe.terraria.demo;

import fr.iut.groupe.terraria.demo.controller.ControleurJeu;
import fr.iut.groupe.terraria.demo.modele.Joueur;
import fr.iut.groupe.terraria.demo.vue.VueJeu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public void start(Stage primaryStage) {
        VueJeu vue = new VueJeu(); // View
        Joueur joueur = new Joueur(100, 260); // Model

        try {
            vue.drawSimpleMap("/fr/iut/groupe/terraria/demo/map.csv", "/fr/iut/groupe/terraria/demo/tileset.png", 32);

        } catch (Exception e) {
            e.printStackTrace();
        }

        ControleurJeu controleur = new ControleurJeu(vue, joueur); // Controller
        Scene scene = new Scene(vue);
        controleur.demarrer(scene);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Jeu MVC - Déplacement + Saut");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
