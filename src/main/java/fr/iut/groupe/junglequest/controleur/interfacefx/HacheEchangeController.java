package fr.iut.groupe.junglequest.controleur.interfacefx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;

import java.net.URL;
import java.util.ResourceBundle;

public class HacheEchangeController implements Initializable {

    @FXML
    private ImageView imgHache;
    @FXML
    private Label lblCout;

    private Joueur joueur;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (imgHache != null) {
            try {
                Image img = new Image(getClass().getResourceAsStream(
                        "/fr.iut.groupe.junglequest/images/items/hache.png"));
                imgHache.setImage(img);
            } catch (Exception e) {
                System.err.println("Image hache introuvable : " + e.getMessage());
            }
        }
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void echanger() {
        if (joueur != null && joueur.getInventaire().retirerItem("Bois", 1)) {
            joueur.getInventaire().ajouterItem("Hache", 1);
        }
        fermer();
    }

    @FXML
    private void fermer() {
        if (stage != null) {
            stage.close();
        }
    }
}
