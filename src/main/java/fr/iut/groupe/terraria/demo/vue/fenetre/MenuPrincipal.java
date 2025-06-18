package fr.iut.groupe.terraria.demo.vue.fenetre;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Lance l'interface principale du jeu à partir d'un fichier FXML.
 * La logique de l'interface est désormais gérée par un contrôleur dédié
 * pour respecter le modèle MVC.
 */
public class MenuPrincipal extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/fr/iut/groupe/terraria/demo/vue/fenetre/MenuPrincipal.fxml"));
        StackPane racine = loader.load();
        fr.iut.groupe.terraria.demo.controleur.interfacefx.MenuPrincipalController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);

        Scene scene = new Scene(racine, 1920, 1080);
        scene.getStylesheets().add(getClass().getResource(
                "/fr/iut/groupe/terraria/demo/styles/menu.css").toExternalForm());

        primaryStage.setTitle("Menu Principal");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
