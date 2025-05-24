package fr.iut.groupe.terraria.demo.modele.personnage;

import fr.iut.groupe.terraria.demo.modele.Ciblable;
import fr.iut.groupe.terraria.demo.modele.item.Block;
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
    private EtatTemporaire etatTemporaire;

    public Joueur(double x, double y, int vieMax, EtatTemporaire etatTemporaire) {
        super(x, y, vieMax, vieMax, 0);
        this.equipementActuel = new Couteau();
        this.inventaire = new Inventaire();
        this.etatTemporaire = etatTemporaire;
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
                int degats = equipementActuel.degatsContre(this.x, this.y, cible, this.getEquipementActuel().getType());
                cible.subirDegats(degats);
                equipementActuel.utiliser();
                changerNullEquipement();
            }
        }
    }

    public void placerBloc(Block bloc, double x, double y, Monde monde) {
        if (inventaire.retirerItem(bloc.getNom())) {
            monde.ajouterBlocPlace(bloc, x, y);
        }
    }

    public boolean changerNullEquipement() {
        boolean changer = false;
        if (this.equipementActuel.estCasse()){
            setEquipementActuel(null);
            changer = true;
        }
        return changer;
    }

    @Override
    public void subirDegats(int degats) {
        if (!this.getEtatTemporaire().isInvincible() && this.getEtatTemporaire().isVulnerable()) {
            degats *= 2;
        }
        super.subirDegats(degats);
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

    public EtatTemporaire getEtatTemporaire() {
        return etatTemporaire;
    }

    public void setEtatTemporaire(EtatTemporaire etatTemporaire) {
        this.etatTemporaire = etatTemporaire;
    }
}

