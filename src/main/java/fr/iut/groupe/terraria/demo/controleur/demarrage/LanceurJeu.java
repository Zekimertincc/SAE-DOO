package fr.iut.groupe.terraria.demo.controleur.demarrage;

import javafx.application.Application;
import javafx.stage.Stage;

import fr.iut.groupe.terraria.demo.controleur.ControleurJeu;

public class LanceurJeu extends Application {
    @Override
    public void start(Stage stage) {
        System.out.println("Initialisation du jeu Jungle Quest...");
        try {
            ControleurJeu.initialiserJeu(stage);
            stage.show();
            System.out.println("Jeu lancé avec succès.");
        } catch (Exception e) {
            System.err.println("Erreur critique : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
