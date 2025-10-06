package fr.iut.groupe.junglequest.modele.personnages;
import fr.iut.groupe.junglequest.modele.Ciblable;
import fr.iut.groupe.junglequest.modele.item.equipement.Couteau;
import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.donnees.ConstantesJeu;

/**
 * Représente le joueur du jeu, héritant du comportement de base d’un personnage.
 */
public class Joueur extends Personnage {

    private boolean estEnAttaque;
    private boolean bouclierActif;
    private final Inventaire inventaire;
    private final IntegerProperty pointsDeVie = new SimpleIntegerProperty();
    private int pointsDeVieMax = 100;
    private Equipement equipementActuel;

    public Joueur(double x, double y) {
        super(x, y, ConstantesJeu.TAILLE_SPRITE, ConstantesJeu.TAILLE_SPRITE);
        this.estEnAttaque = false;
        this.bouclierActif = false;
        this.inventaire = new Inventaire();
        this.pointsDeVie.set(ConstantesJeu.VIE_MAX_JOUEUR);
    }

    public void appliquerDegats(Ciblable cible) {
        int degatsFinal = equipementActuel.calculerDegats(cible, this);
        cible.subirDegats(degatsFinal);
    }

    public void changerNullEquipement() {
        if (this.equipementActuel.estCasse()){
            Couteau c = new Couteau();
            setEquipementActuel(c);
        }
    }

    private void setEquipementActuel(Couteau c) {
        this.equipementActuel = c;
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

    public boolean isBouclierActif() {
        return bouclierActif;
    }

    public void activerBouclier() {
        bouclierActif = true;
    }

    public void desactiverBouclier() {
        bouclierActif = false;
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
        pointsDeVie.set(Math.max(0, pointsDeVie.get() - quantite));
    }

    public void soigner(int quantite) {
        pointsDeVie.set(Math.min(pointsDeVie.get() + quantite, ConstantesJeu.VIE_MAX_JOUEUR));
    }
    public int getVieMax() {
        return this.pointsDeVieMax;
    }
    public EtatTemporaire getEtatTemporaire() {
        return null;
    }
}
