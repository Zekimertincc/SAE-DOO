package fr.iut.groupe.terraria.demo.vue;

import fr.iut.groupe.terraria.demo.controller.ControleurJeu;
import fr.iut.groupe.terraria.demo.modele.Joueur;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TerrariaApplication extends Application {
    @Override
    public void start(Stage stage) {
        VueJeu vue = new VueJeu();
        Joueur joueur = new Joueur(100, 300);
        ControleurJeu controleur = new ControleurJeu(vue, joueur);

        Scene scene = new Scene(vue);
        controleur.demarrer(scene);

        stage.setTitle("Terraria - Démo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
