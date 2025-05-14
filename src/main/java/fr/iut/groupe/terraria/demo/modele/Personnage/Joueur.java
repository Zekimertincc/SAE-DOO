package fr.iut.groupe.terraria.demo.modele.Personnage;

public class Joueur extends Personnage{
    private double vitesseY = 0; // plus y est grand plus le personnage tombe rapidement
    private double largeur = 40, hauteur = 40; // taille du personnage
    private double GRAVITE = 0.5;
    private double SAUT_FORCE = -10;
    private double SOL_Y = 300; // hauteur du sol
    private final double VITESSE_X = 1.5; // ou même 2.0 pour un effet plus doux

    public Joueur(double x, double y, int vieMax) {
        super(x, y, vieMax, vieMax, 0);
    }

    public void gauche() {
        x -= VITESSE_X;
    }

    public void droite() {
        x += VITESSE_X;
    }

    public void appliquerGravite() {
        vitesseY += GRAVITE;
        y += vitesseY;
        if (y > SOL_Y - hauteur) {
            y = SOL_Y - hauteur;
            vitesseY = 0;
        }
    }
    // si le joueur est au sol il peut sauter avec SAUT_FORCE
    public void sauter() {
        if (y >= SOL_Y - hauteur) {
            vitesseY = SAUT_FORCE;
        }
    }
    public int getVie() {
        return vie;
    }

    public int getVieMax() {
        return vieMax;
    }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getLargeur() { return largeur; }
    public double getHauteur() { return hauteur; }
}

