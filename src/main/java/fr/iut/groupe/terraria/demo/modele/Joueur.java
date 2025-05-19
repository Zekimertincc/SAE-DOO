package fr.iut.groupe.terraria.demo.modele;

public class Joueur {
    private double x, y;
    private double vitesseY = 0;
    private double largeur = 40, hauteur = 40;

    private double gravite = -0.5;
    private double hauteurSaut = 10;
    private double hauteurSol = 50;

    public Joueur(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void gauche() { x -= 3; }
    public void droite() { x += 3; }

    public void appliquerGravite() {
        vitesseY += gravite;
        y += vitesseY;
        if (y < hauteurSol) { // si on passe sous le sol
            y = hauteurSol;
            vitesseY = 0;
        }
    }

    public void sauter() {
        if (y <= hauteurSol) {
            vitesseY = hauteurSaut;
        }
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }
}


