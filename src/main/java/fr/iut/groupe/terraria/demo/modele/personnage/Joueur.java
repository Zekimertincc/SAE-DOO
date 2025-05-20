package fr.iut.groupe.terraria.demo.modele.personnage;

import fr.iut.groupe.terraria.demo.modele.Ciblable;
import fr.iut.groupe.terraria.demo.modele.item.equipement.ArmeCraft;
import fr.iut.groupe.terraria.demo.modele.item.equipement.Couteau;
import fr.iut.groupe.terraria.demo.modele.item.equipement.Equipement;
import fr.iut.groupe.terraria.demo.modele.monde.Monde;
import fr.iut.groupe.terraria.demo.modele.Inventaire;

import java.util.HashMap;

public class Joueur extends Personnage{

    private double vitesseY = 0; // plus y est grand plus le personnage tombe rapidement
    private double largeur = 40, hauteur = 40; // taille du personnage
    private double GRAVITE = 0.5;
    private double SAUT_FORCE = -10;
    private double SOL_Y = 300; // hauteur du sol
    private double vitesseH = 1.5; // vitesse de deplacement gauche et droite

    private Equipement equipementActuel;
    private Inventaire inventaire;

    public Joueur(double x, double y, int vieMax) {
        super(x, y, vieMax, vieMax, 0);
        this.equipementActuel = new Couteau();
        this.inventaire = new Inventaire();
    }

    public void gauche(Monde monde) {
        x -= vitesseH;
        monde.mettreAJourCycle();
    }
    public void droite(Monde monde) {
        x += vitesseH;
        monde.mettreAJourCycle();
    }

    public void appliquerGravite() {
        vitesseY += GRAVITE;
        y += vitesseY;
        if (y > SOL_Y - hauteur) {// au sol?
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

    // mettre des degats sur les ennemis/ressources selon l'equipement actuel il y a une portée et des bonus
    public void utiliserEquipementSur(Ciblable cible) {
        boolean peutRecolte = true;

        if (equipementActuel != null){
            if (cible.getTypeCible().equals("Ressource") && !equipementActuel.estOutil()) {
                peutRecolte = false;
            }
            if (peutRecolte) {
                int degats = equipementActuel.degatsContre(this.x, this.y, cible);
                cible.subirDegats(degats);

                equipementActuel.utiliser();
                if (equipementActuel.estCasse()) {
                    equipementActuel = null;
                }
            }
        }
    }

    // joueur peut contruire une arme
    public boolean craftArme(ArmeCraft armeCraftable) {
        boolean estConstruit = false;
        HashMap<String, Integer> ressources = inventaire.getMapItems();

        if (armeCraftable.materiauxRequis(ressources)) {
            if (armeCraftable.construire(inventaire, ressources)){
                estConstruit = true;
            }
        }
        return estConstruit;
    }

// -------------------------------------------------------------------------------------------------------------------------------------
    public double getLargeur() { return largeur; }
    public double getHauteur() { return hauteur; }
    public Equipement getEquipementActuel() {
        return equipementActuel;
    }

    public void setEquipementActuel(Equipement equipement) {
        this.equipementActuel = equipement;
    }
    public void setVitesseX(double vitesseH) {
        this.vitesseH = vitesseH;
    }

    public double getVitesseY() {
        return vitesseY;
    }

    public Inventaire getInventaire() {
        return inventaire;
    }
}

