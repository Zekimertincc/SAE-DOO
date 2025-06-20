package fr.iut.groupe.junglequest.controleur.interfacefx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;
import fr.iut.groupe.junglequest.controleur.interfacefx.InventaireController;

import java.net.URL;
import java.util.ResourceBundle;

public class ForgeController implements Initializable {

    @FXML
    private Label lblEpee;
    @FXML
    private Label lblBouclier;
    @FXML
    private Label lblHache;

    private Joueur joueur;
    private Stage stage;
    private InventaireController inventaireController;

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

    public void setInventaireController(InventaireController inventaireController) {
        this.inventaireController = inventaireController;
    }

    @FXML
    private void forgerEpee() {
        if (joueur != null && joueur.getInventaire().retirerItem("Bois", 5)) {
            joueur.getInventaire().ajouterItem("Epee", 1);
        }
        rafraichir();
        if (inventaireController != null) {
            inventaireController.rafraichir();
        }
    }

    @FXML
    private void forgerBouclier() {
        if (joueur != null && joueur.getInventaire().retirerItem("Bois", 3)) {
            joueur.getInventaire().ajouterItem("Bouclier", 1);
        }
        rafraichir();
        if (inventaireController != null) {
            inventaireController.rafraichir();
        }
    }

    @FXML
    private void forgerHache() {
        if (joueur != null && joueur.getInventaire().retirerItem("Bois", 2)) {
            joueur.getInventaire().ajouterItem("Hache", 1);
        }
        rafraichir();
        if (inventaireController != null) {
            inventaireController.rafraichir();
        }
    }

    private void rafraichir() {
        if (lblEpee != null) {
            boolean ok = joueur != null && joueur.getInventaire().contient("Bois", 5);
            lblEpee.getStyleClass().removeAll("label-available", "label-unavailable");
            lblEpee.getStyleClass().add(ok ? "label-available" : "label-unavailable");
        }
        if (lblBouclier != null) {
            boolean ok = joueur != null && joueur.getInventaire().contient("Bois", 3);
            lblBouclier.getStyleClass().removeAll("label-available", "label-unavailable");
            lblBouclier.getStyleClass().add(ok ? "label-available" : "label-unavailable");
        }
        if (lblHache != null) {
            boolean ok = joueur != null && joueur.getInventaire().contient("Bois", 2);
            lblHache.getStyleClass().removeAll("label-available", "label-unavailable");
            lblHache.getStyleClass().add(ok ? "label-available" : "label-unavailable");
        }
    }

    @FXML
    private void fermer() {
        if (stage != null) {
            stage.close();
        }
        if (inventaireController != null) {
            inventaireController.rafraichir();
        }
    }
}