package fr.iut.groupe.junglequest.modele.personnages;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Classe abstraite représentant un personnage générique avec position, vitesse et sprite.
 * Elle sert de base commune pour le joueur et d'autres entités futures.
 */
public abstract class Personnage {

    protected final DoubleProperty x = new SimpleDoubleProperty();
    protected final DoubleProperty y = new SimpleDoubleProperty();
    protected final DoubleProperty vitesseX = new SimpleDoubleProperty();
    protected final DoubleProperty vitesseY = new SimpleDoubleProperty();
    protected final BooleanProperty versGauche = new SimpleBooleanProperty();
    protected final BooleanProperty estAuSol = new SimpleBooleanProperty();

    private double largeur;
    private double hauteur;

    public Personnage(double x, double y, double largeur, double hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.x.set(x);
        this.y.set(y);
        this.vitesseX.set(0);
        this.vitesseY.set(0);
        this.versGauche.set(false);
        this.estAuSol.set(false);
    }

    /**
     * Met à jour la position en fonction des vitesses actuelles.
     */
    public void mettreAJourPosition() {
        x.set(x.get() + vitesseX.get());
        y.set(y.get() + vitesseY.get());
    }

    /**
     * Applique la gravité si le personnage n'est pas au sol.
     */
    public void appliquerGravite(double gravite, double vitesseChuteMax) {
        if (!estAuSol.get()) {
            vitesseY.set(Math.min(vitesseY.get() + gravite, vitesseChuteMax));
        }
    }

    /**
     * Pose le personnage au sol à une hauteur précise.
     */
    public void poserAuSol(double ySol) {
        y.set(ySol);
        vitesseY.set(0);
        estAuSol.set(true);
    }

    /**
     * Lance un saut si le personnage est au sol.
     */
    public void sauter(double impulsion) {
        if (estAuSol.get()) {
            vitesseY.set(-impulsion);
            estAuSol.set(false);
        }
    }

    /**
     * Déplace vers la gauche.
     */
    public void deplacerGauche(double vitesse) {
        vitesseX.set(-vitesse);
        versGauche.set(true);
    }

    /**
     * Déplace vers la droite.
     */
    public void deplacerDroite(double vitesse) {
        vitesseX.set(vitesse);
        versGauche.set(false);
    }

    /**
     * Arrête le mouvement horizontal.
     */
    public void arreter() {
        vitesseX.set(0);
    }

    // --- Getters utiles ---

    public double getLargeur() { return largeur; }

    public double getHauteur() { return hauteur; }

    public double getX() { return x.get(); }

    public double getY() { return y.get(); }

    public double getVitesseY() { return vitesseY.get(); }
    public double getVitesseX() { return vitesseX.get(); }
    public boolean estAuSol() { return estAuSol.get(); }

    public boolean estVersGauche() { return versGauche.get(); }

    public void setEstAuSol(boolean auSol) {
        this.estAuSol.set(auSol);
    }

    /**
     * Positionne le personnage horizontalement.
     */
    public void setX(double x) {
        this.x.set(x);
    }

    /**
     * Positionne le personnage verticalement.
     */
    public void setY(double y) {
        this.y.set(y);
    }

    /**
     * Modifie la vitesse horizontale du personnage.
     */
    public void setVitesseX(double vitesseX) {
        this.vitesseX.set(vitesseX);
    }

    /**
     * Modifie la vitesse verticale du personnage.
     */
    public void setVitesseY(double vitesseY) {
        this.vitesseY.set(vitesseY);
    }

    // --- Properties ---
    public DoubleProperty xProperty() { return x; }
    public DoubleProperty yProperty() { return y; }
    public DoubleProperty vitesseXProperty() { return vitesseX; }
    public DoubleProperty vitesseYProperty() { return vitesseY; }
    public BooleanProperty versGaucheProperty() { return versGauche; }
    public BooleanProperty estAuSolProperty() { return estAuSol; }


}
