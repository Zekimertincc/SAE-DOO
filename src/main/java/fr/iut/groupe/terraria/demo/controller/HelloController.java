package fr.iut.groupe.terraria.demo.controller;

import fr.iut.groupe.terraria.demo.modele.Joueur;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;


public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Rectangle joueurRect;

    private Joueur joueur;

    @FXML
    public void initialize() {
        joueur = new Joueur(100, 300);
        joueurRect.setLayoutX(joueur.getX());
        joueurRect.setLayoutY(joueur.getY());
    }

    public void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case RIGHT -> joueur.droite();
            case LEFT -> joueur.gauche();
            case SPACE -> joueur.sauter() ;
            default -> {}
        }

        joueurRect.setLayoutX(joueur.getX());
    }

}




