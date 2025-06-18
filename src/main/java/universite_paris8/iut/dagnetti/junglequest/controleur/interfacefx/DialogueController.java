package universite_paris8.iut.dagnetti.junglequest.controleur.interfacefx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DialogueController implements Initializable {

    @FXML
    private TextArea dialogueArea;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (dialogueArea != null) {
            dialogueArea.setText("Bienvenue dans Jungle Quest !\nUtilisez Q/D pour vous deplacer et ESPACE pour sauter.");
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void fermer() {
        if (stage != null) {
            stage.close();
        }
    }
}