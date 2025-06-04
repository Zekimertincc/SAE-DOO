package fr.iut.groupe.terraria.demo.controller;

import fr.iut.groupe.terraria.demo.modele.Inventaire;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.Map;

public class InventaireController {

    @FXML
    private HBox slotBar;

    private Inventaire inventaire;

    public void setInventaire(Inventaire inventaire) {
        this.inventaire = inventaire;
        updateAffichage();
    }

    public void updateAffichage() {
        slotBar.getChildren().clear();

        int i = 0;
        for (Map.Entry<String, Integer> entry : inventaire.getMapItems().entrySet()) {
            String nom = entry.getKey();
            int quantite = entry.getValue();

            StackPane slot = new StackPane();
            slot.setPrefSize(48, 48);
            slot.setStyle("-fx-border-color: white; -fx-background-color: #3a3a3a;");

            Label label = new Label(nom + " x" + quantite);
            label.setStyle("-fx-text-fill: white; -fx-font-size: 10px;");
            StackPane.setAlignment(label, Pos.CENTER);

            slot.getChildren().add(label);
            slotBar.getChildren().add(slot);
            i++;
        }

        for (; i < 9; i++) {
            StackPane emptySlot = new StackPane();
            emptySlot.setPrefSize(48, 48);
            emptySlot.setStyle("-fx-border-color: gray; -fx-background-color: #222;");
            slotBar.getChildren().add(emptySlot);
        }
    }
}
