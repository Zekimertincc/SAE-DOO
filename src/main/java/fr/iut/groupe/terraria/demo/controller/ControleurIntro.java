package fr.iut.groupe.terraria.demo.controller;

import fr.iut.groupe.terraria.demo.modele.Joueur;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

public class ControleurIntro {

    @FXML
    private Label texteBienvenue;

    @FXML
    private Rectangle rectangleJoueur;

    private Joueur joueur;

    @FXML
    public void initialiser() {
        joueur = new Joueur(100, 300);
        mettreAJourPositionJoueur();
    }

    public void gererTouche(KeyEvent evenement) {
        switch (evenement.getCode()) {
            case RIGHT -> joueur.allerADroite();
            case LEFT  -> joueur.allerAGauche();
            case UP    -> joueur.sauter();
            default    -> {}
        }

        joueur.appliquerGravite();
        mettreAJourPositionJoueur();
    }

    private void mettreAJourPositionJoueur() {
        rectangleJoueur.setLayoutX(joueur.getX());
        rectangleJoueur.setLayoutY(joueur.getY());
    }
}
