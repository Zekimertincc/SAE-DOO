package fr.iut.groupe.terraria.demo.modele;

public class Joueur {
    private double x, y;
    private double vitesseY = 0;
    private final double largeur = 40, hauteur = 40;
    private final double GRAVITE = 0.5;
    private final double SAUT_FORCE = -10;
    private final double SOL_Y = 300;

    public Joueur(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void gauche() { x -= 5; }
    public void droite() { x += 5; }

    public void appliquerGravite() {
        vitesseY += GRAVITE;
        y += vitesseY;
        if (y > SOL_Y - hauteur) {
            y = SOL_Y - hauteur;
            vitesseY = 0;
        }
    }

    public void sauter() {
        if (y >= SOL_Y - hauteur) {
            vitesseY = SAUT_FORCE;
        }
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getLargeur() { return largeur; }
    public double getHauteur() { return hauteur; }
}

