package fr.iut.groupe.terraria.demo.controller.demarrage;

import fr.iut.groupe.terraria.demo.controller.ControleurJeu;
import fr.iut.groupe.terraria.demo.modele.Monde;
import fr.iut.groupe.terraria.demo.modele.ressource.*;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import fr.iut.groupe.terraria.demo.modele.personnage.ennemi.Loup;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Monde monde = new Monde("/fr/iut/groupe/terraria/demo/map.csv");
        monde.ajouterRessource(new Arbre(300, 200));
        monde.ajouterRessource(new Arbre(500, 220));
        monde.ajouterRessource(new Arbre(150, 250));
        monde.ajouterRessource(new Roche(400, 300));
        monde.ajouterRessource(new Roche(600, 350));
        monde.ajouterRessource(new CanneSucre(200, 250));
        monde.ajouterRessource(new CanneSucre(500, 280));

        monde.ajouterEnnemi(new Loup(300, 260));

        Joueur joueur = new Joueur(100, 260, 100, new EtatTemporaire(), monde.getMap());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/iut/groupe/terraria/demo/vue/hello-view.fxml"));
        Parent root = fxmlLoader.load();
        ControleurJeu controller = fxmlLoader.getController();
        controller.initJeu(monde, joueur);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}