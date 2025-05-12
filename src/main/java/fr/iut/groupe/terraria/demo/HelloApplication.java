package fr.iut.groupe.terraria.demo;

import fr.iut.groupe.terraria.demo.controller.HelloController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/iut/groupe/terraria/demo/hello-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        // Klavye olaylarını controller'a gönder
        HelloController controller = loader.getController();
        scene.setOnKeyPressed(controller::handleKeyPressed);

        primaryStage.setTitle("SAE Jungle Game - Étape 1 (FXML)");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Odaklanmayı sağlar
        root.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
