package fr.iut.groupe.junglequest.controleur.moteur;

import fr.iut.groupe.junglequest.modele.Environnement;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;

public class ControleurUI {
    private final Environnement env;
    private final Scene scene;

    public ControleurUI(Scene scene, Environnement env) {
        this.scene = scene;
        this.env = env;
    }

    public void ouvrirFenetre(String fxml, String titre) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage fenetre = new Stage();
            fenetre.initOwner(scene.getWindow());
            fenetre.initModality(Modality.WINDOW_MODAL);
            fenetre.setTitle(titre);
            fenetre.setScene(new javafx.scene.Scene(root));
            fenetre.show();
        } catch (Exception e) {
            System.err.println("Erreur chargement UI: " + e.getMessage());
        }
    }
}
