package universite_paris8.iut.dagnetti.junglequest.modele.personnages;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import universite_paris8.iut.dagnetti.junglequest.modele.item.Inventaire;
import universite_paris8.iut.dagnetti.junglequest.modele.donnees.ConstantesJeu;

/**
 * Représente le joueur du jeu, héritant du comportement de base d’un personnage.
 */
public class Joueur extends Personnage {

    private boolean estEnAttaque;
    private boolean bouclierActif;
    private final Inventaire inventaire;
    private final IntegerProperty pointsDeVie = new SimpleIntegerProperty();
    private int pointsDeVieMax =100;

    public Joueur(double x, double y) {
        super(x, y, ConstantesJeu.TAILLE_SPRITE, ConstantesJeu.TAILLE_SPRITE);
        this.estEnAttaque = false;
        this.bouclierActif = false;
        this.inventaire = new Inventaire();
        this.pointsDeVie.set(ConstantesJeu.VIE_MAX_JOUEUR);
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
/**/
    public int getVieMax() {
        return this.pointsDeVieMax;
    }

    public void changerNullEquipement() {
    }

    public EtatTemporaire getEtatTemporaire() {
        return null;
    }
}
