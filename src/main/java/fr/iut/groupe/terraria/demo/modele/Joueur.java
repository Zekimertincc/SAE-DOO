package fr.iut.groupe.terraria.demo.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;

public class Joueur {
    private double x,y; // placement
    private int hp;
    private int largeur, hauteur;
    private double vitesseY; // vitesse
    private double gravite;
    private double sautForce;
    private double solY;

    public Joueur( double x , double y) {
        this.x =  x;
        this.y =  y;
        this.hp = 5;
        this.largeur = 40;
        this.hauteur = 40;
        this.vitesseY = 0;
        this.gravite = 0.5;
        this.sautForce = -10;
        this.solY = 300;
    }

    public void gauche() { x -= 5; }
    public void droite() { x += 5; }
/*
    public void appliquerGravite() {
        vitesseY += gravite;
        y += vitesseY;
        if (y > solY - hauteur) {
            y = solY - hauteur;
            vitesseY = 0;
        }
    }
*/
    public void sauter() {
        y+= 10;

        /*
        if (y >= solY - hauteur) {
            vitesseY = sautForce;
        }

         */
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getLargeur() { return largeur; }
    public double getHauteur() { return hauteur; }

}
