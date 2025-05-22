package fr.iut.groupe.terraria.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class InventaireController {

    @FXML
    private GridPane gridSlots;

    public void initialize() {

        Label slot = (Label) gridSlots.getChildren().get(0);
        slot.setText("🪵 x5");
    }
}
