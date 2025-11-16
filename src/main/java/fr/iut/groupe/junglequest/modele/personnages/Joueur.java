package fr.iut.groupe.junglequest.modele.personnages;
import fr.iut.groupe.junglequest.modele.Ciblable;
import fr.iut.groupe.junglequest.modele.item.equipement.Couteau;
import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;
// import javafx.beans.property.IntegerProperty; // Supprimé
// import javafx.beans.property.SimpleIntegerProperty; // Supprimé
import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.donnees.ConstantesJeu;
import fr.iut.groupe.junglequest.modele.observateurs.Observateur;
import fr.iut.groupe.junglequest.modele.observateurs.TypeChangement;
import fr.iut.groupe.junglequest.modele.personnages.etats.*;
import fr.iut.groupe.junglequest.modele.observateurs.SujetObserve;
import fr.iut.groupe.junglequest.modele.observateurs.SujetObserveImpl;

// Représente le joueur du jeu, héritant du comportement de base d'un personnage.
public class Joueur extends Personnage implements Ciblable, SujetObserve {
    
    // Sujet pour le pattern Observer
    private final SujetObserveImpl sujetObserve = new SujetObserveImpl();

    private boolean estEnAttaque;
    private boolean bouclierActif;
    private final Inventaire inventaire;
    
    // Points de vie du joueur (type Java standard)
    private int pointsDeVie;
    
    private int pointsDeVieMax = 100;
    private String nom = "Joueur";
    private Equipement equipementActuel;
    private boolean estVivant = true;
    private double porteeAttaque = ConstantesJeu.PORTEE_ATTAQUE_JOUEUR;
    // État actuel du joueur (Pattern State)
    private EtatJoueur etatCourant;
    private static final double VITESSE_BASE = 0.5;

    // Constructeur du joueur
    public Joueur(double x, double y) {
        super(x, y, ConstantesJeu.TAILLE_SPRITE, ConstantesJeu.TAILLE_SPRITE);
        this.estEnAttaque = false;
        this.bouclierActif = false;
        this.inventaire = Inventaire.getInstance();
        this.pointsDeVie = ConstantesJeu.VIE_MAX_JOUEUR;
        this.etatCourant = new EtatIdle(); // État initial
    }

    
    // OBSERVER PATTERN - Méthodes de notification
   
    
    // Ajoute un observateur qui sera notifié des changements du joueur
    @Override
    public void ajouterObservateur(Observateur obs) {
        sujetObserve.ajouterObservateur(obs);
    }

    // Retire un observateur de la liste
    @Override
    public void retirerObservateur(Observateur obs) {
        sujetObserve.retirerObservateur(obs);
    }

    // Notifie tous les observateurs d'un changement
    @Override
    public void notifierObservateurs(TypeChangement type) {
        sujetObserve.notifierObservateurs(type);
    }
    
    // MÉTHODES DE BASE
    
    // Retourne le nom du personnage
    @Override
    public String getNom() {
        return nom;
    }

    // Retourne la force de base du personnage
    @Override
    public int getForce() {
        return ConstantesJeu.FORCE_JOUEUR;
    }


    // STATE PATTERN - Gestion des états
    
    // Change l'état courant du joueur (State Pattern)
    public void changerEtat(EtatPersonnage nouvelEtat) {
        if (nouvelEtat instanceof EtatJoueur) {
            this.etatCourant = (EtatJoueur) nouvelEtat;
            notifierObservateurs(TypeChangement.ETAT);
        }
    }

    // Applique les dégâts de l'équipement actuel à une cible
    public void appliquerDegats(Ciblable cible) {
        int degatsFinal = equipementActuel.calculerDegats(cible, this);
        cible.subirDegats(degatsFinal);
    }

    // Remplace l'équipement par un couteau s'il est cassé
    public void changerNullEquipement() {
        if (this.equipementActuel.estCasse()){
            Couteau c = new Couteau();
            setEquipementActuel(c);
        }
    }

    // Définit l'équipement actuellement tenu par le joueur
    private void setEquipementActuel(Equipement equipement) {
        this.equipementActuel = equipement;
    }

    // Vérifie si le joueur est en train d'attaquer
    public boolean estEnAttaque() {
        return estEnAttaque;
    }

    // Démarre une attaque si l'état actuel le permet
    public void attaquer() {
        if (etatCourant.peutAttaquer()) {
            changerEtat(new EtatAttaque());
            estEnAttaque = true;
        }
    }

    // Termine l'attaque en cours
    public void finAttaque() {
        if (estEnAttaque) {
            estEnAttaque = false;
            if (!(etatCourant instanceof EtatMort)) {
                changerEtat(new EtatIdle());
            }
        }
    }
    
    // Alias pour finAttaque
    public void finirAttaque() {
        finAttaque();
    }

    // Vérifie si le bouclier est actif
    public boolean isBouclierActif() {
        return bouclierActif;
    }

    // Active le bouclier
    public void activerBouclier() {
        bouclierActif = true;
    }

    // Désactive le bouclier
    public void desactiverBouclier() {
        bouclierActif = false;
    }

    // Retourne l'inventaire du joueur
    public Inventaire getInventaire() {
        return inventaire;
    }

    // Retourne les points de vie actuels
    public int getPointsDeVie() {
        return pointsDeVie;
    }
    
    // Retourne la portée d'attaque du joueur
    @Override
    public int getPorteeAttaque() {
        return (int) porteeAttaque;
    }
    
    // Calcule les dégâts infligés par le joueur
    @Override
    public int calculerDegats(Ciblable cible) {
        int degats = ConstantesJeu.DEGATS_JOUEUR_LOUP;
        if (equipementActuel != null) {
            degats = equipementActuel.calculerDegats(cible, this);
        }
        return degats;
    }
    
    // Retourne le bonus de dégâts de l'équipement
    public double getBonusEquipement() {
        return equipementActuel != null ? equipementActuel.getBonusAttaque() : 1.0;
    }
    
    // Retourne l'équipement actuel
    public Equipement getEquipementActuel() {
        return equipementActuel;
    }
    
    // Vérifie si le joueur est vivant
    public boolean estVivant() {
        return estVivant;
    }

    // Inflige des dégâts au joueur et notifie les observateurs
    public void subirDegats(int quantite) {
        this.pointsDeVie = Math.max(0, this.pointsDeVie - quantite);
        notifierObservateurs(TypeChangement.POINTS_DE_VIE); 
    }

    // Soigne le joueur et notifie les observateurs
    public void soigner(int quantite) {
        this.pointsDeVie = Math.min(this.pointsDeVie + quantite, ConstantesJeu.VIE_MAX_JOUEUR);
        notifierObservateurs(TypeChangement.POINTS_DE_VIE); 
    }

    // Retourne les points de vie maximum
    public int getVieMax() {
        return this.pointsDeVieMax;
    }

    // Retourne l'état temporaire (buffs/debuffs)
    public EtatTemporaire getEtatTemporaire() {
        return null;
    }

    // Fait mourir le joueur et notifie les observateurs
    public void mourir() {
        this.setPointsDeVie(0);
        notifierObservateurs(TypeChangement.POINTS_DE_VIE); 
        System.out.println("Le joueur est mort.");
    }
    
    // Définit les points de vie et notifie les observateurs
    @Override
    public void setPointsDeVie(int i) {
        this.pointsDeVie = i;
        notifierObservateurs(TypeChangement.POINTS_DE_VIE);
    }

    // Retourne la vitesse de base
    public double getVitesseBase() {
        return VITESSE_BASE;
    }

    // Définit l'orientation du joueur et notifie les observateurs
    public void setVersGauche(boolean versGauche) {
        this.versGauche = versGauche;
        notifierObservateurs(TypeChangement.DIRECTION);
    }

    // LOGIQUE DE JEU - Délégation depuis le Controller
    
    // Gère l'activation/désactivation du bouclier
    public void gererBouclier(boolean activer) {
        if (activer && !estEnAttaque()) {
            activerBouclier();
        } else if (!activer) {
            desactiverBouclier();
        }
    }

    // Gère le mouvement du joueur en fonction des touches pressées
    public void gererMouvement(boolean gauche, boolean droite) {
        if (isBouclierActif() || !etatCourant.peutDeplacer()) {
            arreter();
            return;
        }
        
        if ((gauche || droite) && estAuSol() && !(etatCourant instanceof EtatMarche) && !(etatCourant instanceof EtatAttaque)) {
            changerEtat(new EtatMarche());
        }
        
        if (gauche) {
            setVersGauche(true);
            setVitesseX(-VITESSE_BASE);
        } else if (droite) {
            setVersGauche(false);
            setVitesseX(VITESSE_BASE);
        } else {
            setVitesseX(0);
        }
    }

    // Gère le saut du joueur
    public void gererSaut(boolean toucheSaut) {
        if (toucheSaut && !isBouclierActif() && estAuSol() && etatCourant.peutDeplacer()) {
            EtatSaut etatSaut = new EtatSaut();
            etatSaut.entrer(this);
            changerEtat(etatSaut);
        }
    }

    // Met à jour l'état du joueur (Pattern State)
    public void mettreAJour() {
        etatCourant.mettreAJour(this);
        
        if (getPointsDeVie() <= 0 && !(etatCourant instanceof EtatMort)) {
            changerEtat(new EtatMort());
        }
    }

    // Retourne l'état actuel du joueur
    public EtatPersonnage getEtatCourant() {
        return etatCourant;
    }

    // Méthode unifiée pour encaisser des dégâts
    public void encaisserDegats(int points) {
        subirDegats(points);
    }
}
