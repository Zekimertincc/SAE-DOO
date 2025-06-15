package fr.iut.groupe.terraria.demo.modele.personnage;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Personnage {
    protected DoubleProperty x, y;
    protected double vitesseY;
    protected double vitesseX;
    protected IntegerProperty vie;
    protected int vieMax;
    protected int degats;

    public Personnage(double x, double y, double vitesseX, double vitesseY, int vie, int vieMax, int degats) {
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        this.vitesseX = vitesseX;
        this.vitesseY = vitesseY;
        this.vie = new SimpleIntegerProperty(vie);
        this.vieMax = vieMax;
        this.degats = degats;
    }

    /*
        La fonction prend en parametre le nombre de vie, et l'ajoute sur le personnage
        Si la vie depasse 5 coeurs, alors le perso à 5 coeur, la vie est en dessous 0 alors le personnage a 0 coeur
    */
    public void gagnerVie(int quantite) {
        vie.set(vie.get() + quantite);
        if (vie.get() > vieMax) {
            vie.set(vieMax);
        }
        if (vie.get() < 0) {
            vie.set(0);
        }
    }
    // utilise la fonction gagner de la vie mais envoie un nombre negatif pour faire perdre de la vie au joueur
    public void subirDegats(int degats) {
        gagnerVie(-degats);
    }

    // verifie si le personnage est mort
    public boolean estMort() {
        return vie.get() <= 0;
    }

    public int getVie() { return vie.get(); }
    public IntegerProperty vieProperty() { return vie; }
    public int getVieMax() {
        return vieMax;
    }
    public double getX() { return x.get(); }
    public void setX(double x) { this.x.set(x); }
    public DoubleProperty xProperty() { return x; }
    public double getY() { return y.get(); }
    public void setY(double y) { this.y.set(y); }
    public DoubleProperty yProperty() { return y; }
}
