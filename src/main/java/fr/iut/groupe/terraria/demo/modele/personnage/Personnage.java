package fr.iut.groupe.terraria.demo.modele.personnage;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class Personnage {
    protected DoubleProperty x = new SimpleDoubleProperty();
    protected DoubleProperty y = new SimpleDoubleProperty();
    protected double vitesseY;
    protected double vitesseX;
    protected int vie, vieMax;
    protected int degats;

    public Personnage(double x, double y, double vitesseX, double vitesseY, int vie, int vieMax, int degats) {
        this.x.set(x);
        this.y.set(y);
        this.vitesseX = vitesseX;
        this.vitesseY = vitesseY;
        this.vie = vie;
        this.vieMax = vieMax;
        this.degats = degats;
    }

    /*
        La fonction prend en paramètre le nombre de vie, et l'ajoute sur le personnage.
        Si la vie dépasse 5 coeurs, alors le perso a 5 coeurs. Si la vie est en dessous de 0, alors il a 0 coeur.
    */
    public void gagnerVie(int quantite) {
        vie += quantite;
        if (vie > vieMax) vie = vieMax;
        if (vie < 0) vie = 0;
    }

    // utilise la fonction gagner de la vie mais envoie un nombre négatif pour faire perdre de la vie au joueur
    public void subirDegats(int degats) {
        gagnerVie(-degats);
    }

    // vérifie si le personnage est mort
    public boolean estMort() {
        return vie <= 0;
    }

    public int getVie() { return vie; }
    public int getVieMax() { return vieMax; }

    public double getX() { return x.get(); }
    public void setX(double x) { this.x.set(x); }
    public DoubleProperty xProperty() { return x; }

    public double getY() { return y.get(); }
    public void setY(double y) { this.y.set(y); }
    public DoubleProperty yProperty() { return y; }

    public void setVitesseX(double vitesseX) {
        this.vitesseX = vitesseX;
    }
}
