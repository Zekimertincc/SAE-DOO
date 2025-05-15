package fr.iut.groupe.terraria.demo.modele;

public class Joueur {
    private double positionX, positionY;
    private double vitesseVerticale = 0;
    private final double largeur = 40;
    private final double hauteur = 40;

    private final double GRAVITE = 0.5;
    private final double FORCE_SAUT = -10;
    private final double SOL_Y = 300;

    public Joueur(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void allerAGauche() {
        positionX -= 5;
    }

    public void allerADroite() {
        positionX += 5;
    }

    public void appliquerGravite() {
        vitesseVerticale += GRAVITE;
        positionY += vitesseVerticale;

        if (positionY > SOL_Y - hauteur) {
            positionY = SOL_Y - hauteur;
            vitesseVerticale = 0;
        }
    }

    public void sauter() {
        if (positionY >= SOL_Y - hauteur) {
            vitesseVerticale = FORCE_SAUT;
        }
    }

    public double getX() {
        return positionX;
    }

    public double getY() {
        return positionY;
    }

    public double getLargeur() {
        return largeur;
    }

    public double getHauteur() {
        return hauteur;
    }
}
