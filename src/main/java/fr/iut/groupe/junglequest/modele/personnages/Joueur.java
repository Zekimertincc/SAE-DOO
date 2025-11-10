package fr.iut.groupe.junglequest.modele.personnages;
import fr.iut.groupe.junglequest.modele.Ciblable;
import fr.iut.groupe.junglequest.modele.item.equipement.Couteau;
import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.donnees.ConstantesJeu;
import fr.iut.groupe.junglequest.modele.observateurs.Observateur;
import fr.iut.groupe.junglequest.modele.observateurs.TypeChangement;
import fr.iut.groupe.junglequest.modele.personnages.etats.*;
import fr.iut.groupe.junglequest.modele.observateurs.SujetObserve;
import fr.iut.groupe.junglequest.modele.observateurs.SujetObserveImpl;

/**
 * Représente le joueur du jeu, héritant du comportement de base d'un personnage.
 * 
 * Design Patterns utilisés:
 * - State Pattern: Gestion des états du joueur (idle, marche, attaque, saut, mort)
 * - Observer Pattern: Notification des changements au View (points de vie, position, etc.)
 * - Singleton Pattern: Utilise l'inventaire unique
 * 
 * Responsabilités (MVC):
 * - Gestion de l'état du joueur
 * - Calcul des dégâts et de la défense
 * - Gestion de l'inventaire
 * - Notification des observateurs lors de changements
 */
public class Joueur extends Personnage implements Ciblable, SujetObserve {
    // Observer Pattern: Délégation de la gestion des observateurs
    private final SujetObserveImpl sujetObserve = new SujetObserveImpl();

    private boolean estEnAttaque;
    private boolean bouclierActif;
    private final Inventaire inventaire;
    private final IntegerProperty pointsDeVie = new SimpleIntegerProperty();
    private int pointsDeVieMax = 100;
    private String nom = "Joueur";
    private Equipement equipementActuel;
    private boolean estVivant = true;
    private double porteeAttaque = ConstantesJeu.PORTEE_ATTAQUE_JOUEUR;
    // State Pattern: État courant du joueur
    private EtatJoueur etatCourant;
    private static final double VITESSE_BASE = 0.5;

    /**
     * Constructeur du joueur
     * @param x Position X initiale
     * @param y Position Y initiale
     */
    public Joueur(double x, double y) {
        super(x, y, ConstantesJeu.TAILLE_SPRITE, ConstantesJeu.TAILLE_SPRITE);
        this.estEnAttaque = false;
        this.bouclierActif = false;
        this.inventaire = Inventaire.getInstance();
        this.pointsDeVie.set(ConstantesJeu.VIE_MAX_JOUEUR);
        this.etatCourant = new EtatIdle(); // État initial
    }

    // ============================================================
    // OBSERVER PATTERN - Méthodes de notification
    // ============================================================
    
    /**
     * Ajoute un observateur qui sera notifié des changements du joueur
     * @param obs L'observateur à ajouter
     */
    @Override
    public void ajouterObservateur(Observateur obs) {
        sujetObserve.ajouterObservateur(obs);
    }

    /**
     * Retire un observateur de la liste
     * @param obs L'observateur à retirer
     */
    @Override
    public void retirerObservateur(Observateur obs) {
        sujetObserve.retirerObservateur(obs);
    }

    /**
     * Notifie tous les observateurs d'un changement
     * @param type Le type de changement (VIE, POSITION, ETAT, etc.)
     */
    @Override
    public void notifierObservateurs(TypeChangement type) {
        sujetObserve.notifierObservateurs(type);
    }
    
    // ============================================================
    // MÉTHODES DE BASE
    // ============================================================
    
    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public int getForce() {
        return ConstantesJeu.FORCE_JOUEUR;
    }

    // ============================================================
    // STATE PATTERN - Gestion des états
    // ============================================================
    
    /**
     * Change l'état courant du joueur (State Pattern)
     * Notifie automatiquement les observateurs du changement
     * @param nouvelEtat Le nouvel état à appliquer
     */
    public void changerEtat(EtatPersonnage nouvelEtat) {
        if (nouvelEtat instanceof EtatJoueur) {
            this.etatCourant = (EtatJoueur) nouvelEtat;
            notifierObservateurs(TypeChangement.ETAT);
        }
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

    private void setEquipementActuel(Equipement equipement) {
        this.equipementActuel = equipement;
    }

    public boolean estEnAttaque() {
        return estEnAttaque;
    }

    /**
     * Démarre une attaque si l'état actuel le permet
     */
    public void attaquer() {
        if (etatCourant.peutAttaquer()) {
            changerEtat(new EtatAttaque());
            estEnAttaque = true;
        }
    }

    /**
     * Termine l'attaque en cours
     */
    public void finAttaque() {
        if (estEnAttaque) {
            estEnAttaque = false;
            if (!(etatCourant instanceof EtatMort)) {
                changerEtat(new EtatIdle());
            }
        }
    }
    
    public void finirAttaque() {
        finAttaque();
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
    
    @Override
    public int getPorteeAttaque() {
        return (int) porteeAttaque;
    }
    
    @Override
    public int calculerDegats(Ciblable cible) {
        int degats = ConstantesJeu.DEGATS_JOUEUR_LOUP;
        if (equipementActuel != null) {
            degats = equipementActuel.calculerDegats(cible, this);
        }
        return degats;
    }
    
    public double getBonusEquipement() {
        return equipementActuel != null ? equipementActuel.getBonusAttaque() : 1.0;
    }
    
    public Equipement getEquipementActuel() {
        return equipementActuel;
    }
    
    public boolean estVivant() {
        return estVivant;
    }

    public IntegerProperty pointsDeVieProperty() {
        return pointsDeVie;
    }

    public void subirDegats(int quantite) {
        pointsDeVie.set(Math.max(0, pointsDeVie.get() - quantite));
        notifierObservateurs(TypeChangement.POINTS_DE_VIE);
    }

    public void soigner(int quantite) {
        pointsDeVie.set(Math.min(pointsDeVie.get() + quantite, ConstantesJeu.VIE_MAX_JOUEUR));
        notifierObservateurs(TypeChangement.POINTS_DE_VIE);
    }

    public int getVieMax() {
        return this.pointsDeVieMax;
    }
    public EtatTemporaire getEtatTemporaire() {
        return null;
    }
    public void mourir() {
        this.setPointsDeVie(0);
        notifierObservateurs(TypeChangement.POINTS_DE_VIE);
        System.out.println("Le joueur est mort.");
    }
    public void setPointsDeVie(int i) {
        this.pointsDeVie.set(i);
    }

    public double getVitesseBase() {
        return VITESSE_BASE;
    }

    public void setVersGauche(boolean versGauche) {
        this.versGaucheProperty().set(versGauche);
        notifierObservateurs(TypeChangement.DIRECTION);
    }

    // ============================================================
    // LOGIQUE DE JEU - Délégation depuis le Controller
    // ============================================================
    
    /**
     * Gère l'activation/désactivation du bouclier
     * @param activer true pour activer, false pour désactiver
     */
    public void gererBouclier(boolean activer) {
        if (activer && !estEnAttaque()) {
            activerBouclier();
        } else if (!activer) {
            desactiverBouclier();
        }
    }

    /**
     * Gère le mouvement du joueur en fonction des touches pressées
     * Respecte les contraintes de l'état actuel (State Pattern)
     * @param gauche true si la touche gauche est pressée
     * @param droite true si la touche droite est pressée
     */
    public void gererMouvement(boolean gauche, boolean droite) {
        // State Pattern: Vérifie si le déplacement est autorisé dans l'état actuel
        if (isBouclierActif() || !etatCourant.peutDeplacer()) {
            arreter();
            return;
        }
        
        // Transition vers EtatMarche si on commence à bouger
        if ((gauche || droite) && estAuSol() && !(etatCourant instanceof EtatMarche) && !(etatCourant instanceof EtatAttaque)) {
            changerEtat(new EtatMarche());
        }
        
        // Set velocity based on input
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

    /**
     * Gère le saut du joueur
     * @param toucheSaut true si la touche de saut est pressée
     */
    public void gererSaut(boolean toucheSaut) {
        if (toucheSaut && !isBouclierActif() && estAuSol() && etatCourant.peutDeplacer()) {
            EtatSaut etatSaut = new EtatSaut();
            etatSaut.entrer(this);  // Appeler entrer() pour effectuer le saut
            changerEtat(etatSaut);
        }
    }

    /**
     * Met à jour l'état du joueur (State Pattern)
     * Appelé chaque frame par le ControleurJeu
     * Délègue le comportement à l'état courant
     */
    public void mettreAJour() {
        // State Pattern: L'état courant gère son propre comportement
        etatCourant.mettreAJour(this);
        
        // Vérification automatique de la mort
        if (getPointsDeVie() <= 0 && !(etatCourant instanceof EtatMort)) {
            changerEtat(new EtatMort());
        }
    }

    /**
     * Retourne l'état actuel du joueur (State Pattern)
     * @return L'état courant
     */
    public EtatPersonnage getEtatCourant() {
        return etatCourant;
    }

    /**
     * Méthode unifiée pour encaisser des dégâts
     * Notifie automatiquement les observateurs
     * @param points Nombre de points de vie à retirer
     */
    public void encaisserDegats(int points) {
        subirDegats(points);
    }



}
