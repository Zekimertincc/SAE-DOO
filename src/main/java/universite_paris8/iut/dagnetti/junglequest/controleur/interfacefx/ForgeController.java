package universite_paris8.iut.dagnetti.junglequest.controleur.interfacefx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Joueur;

import java.net.URL;
import java.util.ResourceBundle;

public class ForgeController implements Initializable {

    @FXML
    private Label lblEpee;
    @FXML
    private Label lblBouclier;

    private Joueur joueur;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rafraichir();
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
        rafraichir();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void forgerEpee() {
        if (joueur != null && joueur.getInventaire().retirerItem("Bois", 5)) {
            joueur.getInventaire().ajouterItem("Epee", 1);
        }
        rafraichir();
    }

    @FXML
    private void forgerBouclier() {
        if (joueur != null && joueur.getInventaire().retirerItem("Bois", 3)) {
            joueur.getInventaire().ajouterItem("Bouclier", 1);
        }
        rafraichir();
    }

    private void rafraichir() {
        if (lblEpee != null) {
            boolean ok = joueur != null && joueur.getInventaire().contient("Bois", 5);
            lblEpee.setStyle(ok ? "-fx-text-fill: white;" : "-fx-text-fill: red;");
        }
        if (lblBouclier != null) {
            boolean ok = joueur != null && joueur.getInventaire().contient("Bois", 3);
            lblBouclier.setStyle(ok ? "-fx-text-fill: white;" : "-fx-text-fill: red;");
        }
    }

    @FXML
    private void fermer() {
        if (stage != null) {
            stage.close();
        }
    }
}