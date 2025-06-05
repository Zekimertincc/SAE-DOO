package fr.iut.groupe.terraria.demo.controller.interfacefx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import fr.iut.groupe.terraria.demo.modele.item.Inventaire;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class InventaireController implements Initializable {

    @FXML
    private HBox slotBar;

    private Inventaire inventaire;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            slotBar.getStylesheets().add(getClass().getResource(
                    "/fr/iut/groupe/terraria/demo/styles/inventaire.css"
            ).toExternalForm());
            System.out.println("✅ Feuille de style de l’inventaire chargée.");
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du chargement du CSS de l’inventaire.");
        }
    }

    /**
     * Définit l’inventaire et l’affiche graphiquement dans l’interface.
     */
    public void setInventaire(Inventaire inventaire) {
        this.inventaire = inventaire;

        if (inventaire == null) {
            System.err.println("❌ Inventaire non initialisé (null).");
            return;
        }

        afficherSlots();
        System.out.println("📦 Inventaire appliqué au contrôleur. Contenu : " + inventaire.getMapItems().size() + " item(s).");
    }

    /**
     * Met à jour les cases d'inventaire à partir des données internes.
     */
    private void afficherSlots() {
        slotBar.getChildren().clear();

        //  Affichage des objets possédés
        for (Map.Entry<String, Integer> entry : inventaire.getMapItems().entrySet()) {
            String nom = entry.getKey();
            int quantite = entry.getValue();

            StackPane slot = creerSlot(nom, quantite);
            slotBar.getChildren().add(slot);
        }

        //  Complétion visuelle avec des slots vides
        int slotsUtilisés = inventaire.getMapItems().size();
        int slotsTotaux = 9;

        for (int i = slotsUtilisés; i < slotsTotaux; i++) {
            StackPane vide = creerSlotVide();
            slotBar.getChildren().add(vide);
        }
    }

    /**
     * Crée un slot rempli visuellement avec une icône et une étiquette de quantité.
     */
    private StackPane creerSlot(String nom, int quantite) {
        StackPane slot = new StackPane();
        slot.getStyleClass().add("slot-rempli");

        //  Chargement de l’image de l’objet
        String cheminImage = "/fr/iut/groupe/terraria/demo/images/items/" + nom.toLowerCase() + ".png";
        ImageView icone = null;

        try {
            Image image = new Image(getClass().getResourceAsStream(cheminImage));
            icone = new ImageView(image);
            icone.setFitWidth(24);
            icone.setFitHeight(24);
        } catch (Exception e) {
            System.err.println("⚠️ Icône introuvable : \"" + nom + "\" → Attendu à : " + cheminImage);
        }

        //  Quantité
        Label label = new Label("x" + quantite);
        label.getStyleClass().add("label-item");
        StackPane.setAlignment(label, Pos.BOTTOM_RIGHT);

        if (icone != null) {
            slot.getChildren().add(icone);
        } else {
            Label erreur = new Label("❌");
            erreur.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            slot.getChildren().add(erreur);
        }

        slot.getChildren().add(label);
        return slot;
    }

    /**
     * Crée une case vide esthétique pour compléter l’inventaire.
     */
    private StackPane creerSlotVide() {
        StackPane empty = new StackPane();
        empty.getStyleClass().add("slot-vide");
        return empty;
    }
}
