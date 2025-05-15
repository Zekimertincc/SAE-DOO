package fr.iut.groupe.terraria.demo.modele.personnage;

import fr.iut.groupe.terraria.demo.modele.item.equipement.Couteau;
import fr.iut.groupe.terraria.demo.modele.item.equipement.Equipement;

public class Joueur extends Personnage{

    private double vitesseY = 0; // plus y est grand plus le personnage tombe rapidement
    private double largeur = 40, hauteur = 40; // taille du personnage
    private double GRAVITE = 0.5;
    private double SAUT_FORCE = -10;
    private double SOL_Y = 300; // hauteur du sol
    private final double VITESSE_X = 1.5; // vitesse de deplacement gauche et droite

    private Equipement equipementActuel;

    public Joueur(double x, double y, int vieMax) {
        super(x, y, vieMax, vieMax, 0);
        this.equipementActuel = new Couteau();
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

    public void utiliserEquipementSur(String cible) {
        if (equipementActuel != null) {
            int degats = equipementActuel.getDegatsContre(cible);
            equipementActuel.utiliser(); // réduit la durabilité

            System.out.println("Tu as utilisé " + equipementActuel.getNom() + " sur " + cible + " (dégâts = " + degats + ")");

            if (equipementActuel.estCasse()) {
                System.out.println("Ton " + equipementActuel.getNom() + " est cassé !");
                equipementActuel = null;
            }
        } else {
            System.out.println("Aucun équipement sélectionné !");
        }
    }

    public double getLargeur() { return largeur; }
    public double getHauteur() { return hauteur; }
    public Equipement getEquipementActuel() {
        return equipementActuel;
    }

    public void setEquipementActuel(Equipement equipement) {
        this.equipementActuel = equipement;
    }

}

