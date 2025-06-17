package fr.iut.groupe.terraria.demo.modele.personnages;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.ImageView;
import fr.iut.groupe.terraria.demo.modele.item.Inventaire;
import fr.iut.groupe.terraria.demo.modele.donnees.ConstantesJeu;

/**
 * Représente le joueur du jeu, héritant du comportement de base d’un personnage.
 */
public class Joueur extends Personnage {

    private boolean estEnAttaque;
    private final Inventaire inventaire;
    private final IntegerProperty pointsDeVie;

    public Joueur(ImageView sprite, double x, double y) {
        super(sprite, x, y);
        this.estEnAttaque = false;
        this.inventaire = new Inventaire();
        this.pointsDeVie = new SimpleIntegerProperty(ConstantesJeu.VIE_MAX_JOUEUR);
    }

    public boolean estEnAttaque() {
        return estEnAttaque;
    }

    public void attaquer() {
        estEnAttaque = true;
    }

    public void finAttaque() {
        estEnAttaque = false;
    }

    public Inventaire getInventaire() {
        return inventaire;
    }

    public int getPointsDeVie() {
        return pointsDeVie.get();
    }

    public IntegerProperty pointsDeVieProperty() {
        return pointsDeVie;
    }

    public void subirDegats(int quantite) {
        int nouvelleVie = pointsDeVie.get() - quantite;
        pointsDeVie.set(Math.max(nouvelleVie, 0));
    }

    public void soigner(int quantite) {
        int nouvelleVie = pointsDeVie.get() + quantite;
        pointsDeVie.set(Math.min(nouvelleVie, ConstantesJeu.VIE_MAX_JOUEUR));
    }
}
