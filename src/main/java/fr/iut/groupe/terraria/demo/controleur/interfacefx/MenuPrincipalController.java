package fr.iut.groupe.terraria.demo.controleur.interfacefx;

import fr.iut.groupe.terraria.demo.controleur.demarrage.LanceurJeu;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

/**
 * Contrôleur de l'interface du menu principal.
 * Gère les animations et les actions des boutons.
 */
public class MenuPrincipalController {

    @FXML
    private ImageView backgroundView;
    @FXML
    private Text titre;
    @FXML
    private Button jouerButton;
    @FXML
    private Button quitterButton;

    private AudioClip ambiance;
    private Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private void initialize() {
        Image background = new Image(getClass().getResourceAsStream(
                "/fr/iut/groupe/terraria/demo/images/menu_principal_forest.png"));
        backgroundView.setImage(background);
        backgroundView.setPreserveRatio(false);
        backgroundView.setFitWidth(1920);
        backgroundView.setFitHeight(1080);

        FadeTransition fadeTitre = new FadeTransition(Duration.millis(1200), titre);
        fadeTitre.setFromValue(0);
        fadeTitre.setToValue(1);
        fadeTitre.setDelay(Duration.millis(300));

        TranslateTransition slideTitre = new TranslateTransition(Duration.millis(1000), titre);
        slideTitre.setFromY(-50);
        slideTitre.setToY(0);
        slideTitre.setDelay(Duration.millis(300));

        fadeTitre.play();
        slideTitre.play();

        ambiance = new AudioClip(getClass().getResource(
                "/fr/iut/groupe/terraria/demo/sons/menu_jungle.mp3").toExternalForm());
        ambiance.setCycleCount(AudioClip.INDEFINITE);
        ambiance.setVolume(0.4);
        ambiance.play();

        appliquerEffetSurvol(jouerButton);
        appliquerEffetSurvol(quitterButton);

        jouerButton.setOnAction(e -> {
            ambiance.stop();
            try {
                new LanceurJeu().start(new Stage());
                primaryStage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        quitterButton.setOnAction(e -> {
            ambiance.stop();
            primaryStage.close();
        });
    }

    private void appliquerEffetSurvol(Button bouton) {
        DropShadow effet = new DropShadow(20, Color.web("#fff58a", 0.6));
        effet.setSpread(0.3);

        bouton.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            bouton.setEffect(effet);
            FadeTransition fade = new FadeTransition(Duration.millis(200), bouton);
            fade.setFromValue(1.0);
            fade.setToValue(0.8);
            fade.play();
        });

        bouton.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            bouton.setEffect(null);
            FadeTransition fade = new FadeTransition(Duration.millis(200), bouton);
            fade.setFromValue(0.8);
            fade.setToValue(1.0);
            fade.play();
        });
    }
}
