package fr.iut.groupe.terraria.demo.modele.personnage;

import fr.iut.groupe.terraria.demo.modele.item.Inventaire;
import fr.iut.groupe.terraria.demo.modele.item.equipement.Equipement;
import javafx.scene.image.ImageView;

/**
 * Représente le joueur du jeu, héritant du comportement de base d’un personnage.
 */
public class Joueur extends Personnage {

    private boolean estEnAttaque;
    private final Inventaire inventaire;

    private Equipement equipementActuel;
    private EtatTemporaire etatTemporaire;

    public Joueur(ImageView sprite, double x, double y, int vieMax,int degats, EtatTemporaire etatTemporaire) {
        super(sprite, x, y, vieMax, vieMax, degats );
        this.estEnAttaque = false;
        this.etatTemporaire = etatTemporaire;
        this.inventaire = new Inventaire();
    }

    public void changerNullEquipement() {
        if (this.equipementActuel.estCasse()){
            setEquipementActuel(null);
        }
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
    public void setEquipementActuel(Equipement equipement) {
        this.equipementActuel = equipement;
    }
    public EtatTemporaire getEtatTemporaire() {
        return etatTemporaire;
    }
}
