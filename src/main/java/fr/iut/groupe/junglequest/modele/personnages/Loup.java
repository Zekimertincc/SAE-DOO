package fr.iut.groupe.junglequest.modele.personnages;

// import javafx.beans.property.IntegerProperty; // Supprimé
// import javafx.beans.property.SimpleIntegerProperty; // Supprimé
import java.util.Random;
import fr.iut.groupe.junglequest.modele.donnees.ConstantesJeu;
import fr.iut.groupe.junglequest.modele.monde.AStar;
import fr.iut.groupe.junglequest.modele.carte.Carte;
import fr.iut.groupe.junglequest.modele.personnages.strategies.*;

// Imports pour le Pattern Observer (ajoutés)
import fr.iut.groupe.junglequest.modele.observateurs.SujetObserve;
import fr.iut.groupe.junglequest.modele.observateurs.SujetObserveImpl;
import fr.iut.groupe.junglequest.modele.observateurs.Observateur;
import fr.iut.groupe.junglequest.modele.observateurs.TypeChangement;

// Représente un ennemi de type loup.
// Implémente SujetObserve pour notifier la vue (ex: barre de vie).
public class Loup extends Personnage implements SujetObserve {

    // Délégué pour le Pattern Observer
    private final SujetObserveImpl sujetObserve = new SujetObserveImpl();

    private final int degats;
    // Remplacement de IntegerProperty par int
    private int pointsDeVie;
    private boolean estMort = false;
    
    // Getters pour Strategy pattern
    public int getDirectionAttaque() { return directionAttaque; }
    public double getCibleAttaqueX() { return cibleAttaqueX; }
    public int getDelaiAvantAttaque() { return delaiAvantAttaque; }
    public void setDelaiAvantAttaque(int delai) { this.delaiAvantAttaque = delai; }
    public int getDureeAction() { return dureeAction; }
    public Random getRandom() { return random; }
    public boolean estMort() { return estMort; }
    public String getAnimationState() { return animationState; }
    public void setAnimationState(String state) { this.animationState = state; }
    
    // Rayon de détection du joueur (en pixels).
    private final double zoneDetection = 450;

    // Vitesse horizontale du loup.
    private final double vitesseMarche = 0.8;
    private final double vitesseCourse = 1.3;
    
    // État de l'animation (pour la Vue)
    private String animationState = "walk";
    private boolean enAttaque = false;
    // Direction prise lors du lancement d'une attaque
    private int directionAttaque = 0;
    // Position visée lors du début de l'attaque.
    private double cibleAttaqueX = 0;
    // Compteur gérant le temps d'attente avant une nouvelle attaque.
    private int delaiAvantAttaque = ConstantesJeu.DELAI_AVANT_ATTAQUE_LOUP;
    private final Random random = new Random();
    private int dureeAction = 0;
    // Temps restant d'une éventuelle pause dans la poursuite du joueur.
    private int pausePoursuite = 0;

    private ComportementLoup strategie;
    
    // Constructeur du loup avec toutes ses caractéristiques
    public Loup(double x, double y, double largeur, double hauteur, int degats) {
        super(x, y, largeur, hauteur);
        this.degats = degats;
        // assignation directe de l'entier
        this.pointsDeVie = ConstantesJeu.VIE_MAX_LOUP;
        this.strategie = new ComportementAgressif();
        this.animationState = "walk";
    }

    // Définit la durée d'une action de patrouille
    public void setDureeAction(int duree) {
        this.dureeAction = duree;
    }

    // Met à jour le déplacement du loup en fonction de la position du joueur.
    public void mettreAJourIA(Joueur joueur, Carte carte) {
        if (strategie != null) {
            strategie.mettreAJour(this, joueur, carte);
        }
    }

    // Gère la pause du loup après une attaque
    public void gererPause() {
        pausePoursuite--;
        arreter();
        animationState = "walk";
    }

    // Gère l'interaction (poursuite ou attaque) avec le joueur
    public void gererInteractionAvecJoueur(double distance, Joueur joueur, Carte carte) {
        if (Math.abs(distance) <= ConstantesJeu.DISTANCE_ARRET_LOUP) {
            gererApprocheAttaque(distance, joueur);
        } else {
            gererDeplacement(distance, joueur, carte);
        }
    }
    
    // Continue le mouvement de charge lors d'une attaque
    public void poursuivreAttaque() {
        if (directionAttaque >= 0) {
            if (getX() < cibleAttaqueX) {
                double step = Math.min(vitesseCourse, cibleAttaqueX - getX());
                deplacerDroite(step);
            } else {
                arreter();
            }
        } else {
            if (getX() > cibleAttaqueX) {
                double step = Math.min(vitesseCourse, getX() - cibleAttaqueX);
                deplacerGauche(step);
            } else {
                arreter();
            }
        }
    }

    // Gère le loup lorsqu'il est proche du joueur (prêt à attaquer)
    private void gererApprocheAttaque(double distance, Joueur joueur) {
        arreter();
        if (delaiAvantAttaque > 0) {
            delaiAvantAttaque--;
            if (distance > 0) {
                deplacerDroite(vitesseMarche);
            } else {
                deplacerGauche(vitesseMarche);
            }
        } else {
            attaquer(joueur.getX());
            delaiAvantAttaque = ConstantesJeu.DELAI_AVANT_ATTAQUE_LOUP;
        }
    }

    // Gère le déplacement du loup (patrouille ou poursuite)
    private void gererDeplacement(double distance, Joueur joueur, Carte carte) {
        if (Math.abs(distance) <= zoneDetection) {
            suivreChemin(joueur, carte);
            delaiAvantAttaque = ConstantesJeu.DELAI_AVANT_ATTAQUE_LOUP;
        } else {
            animationState = "walk";
            if (dureeAction <= 0) {
                int action = random.nextInt(3);
                if (action == 0) {
                    arreter();
                } else if (action == 1) {
                    deplacerGauche(vitesseMarche);
                } else {
                    deplacerDroite(vitesseMarche);
                }
                dureeAction = random.nextInt(120) + 60;
            } else {
                dureeAction--;
            }
            delaiAvantAttaque = ConstantesJeu.DELAI_AVANT_ATTAQUE_LOUP;
        }
    }

    // Utilise A* pour trouver le chemin vers le joueur
    private void suivreChemin(Joueur joueur, fr.iut.groupe.junglequest.modele.carte.Carte carte) {
        int startX = (int) (getX() / ConstantesJeu.TAILLE_TUILE);
        int startY = (int) (getY() / ConstantesJeu.TAILLE_TUILE);
        int goalX = (int) (joueur.getX() / ConstantesJeu.TAILLE_TUILE);
        int goalY = (int) (joueur.getY() / ConstantesJeu.TAILLE_TUILE);

        java.util.List<int[]> chemin = AStar.chercherChemin(carte, startX, startY, goalX, goalY);
        if (chemin.size() > 1) {
            int[] next = chemin.get(1);
            double cibleX = next[0] * ConstantesJeu.TAILLE_TUILE;
            animationState = "run";
            if (cibleX > getX()) {
                deplacerDroite(vitesseCourse);
            } else if (cibleX < getX()) {
                deplacerGauche(vitesseCourse);
            } else {
                arreter();
            }
        } else {
            animationState = "run";
            if (joueur.getX() > getX()) {
                deplacerDroite(vitesseCourse);
            } else {
                deplacerGauche(vitesseCourse);
            }
        }
    }

    // Dégâts infligés au joueur lors d'un contact.
    public int getDegats() {
        return degats;
    }

    // Vérifie si le loup est en train d'attaquer
    public boolean estEnAttaque() { return enAttaque; }

    // Déclenche l'état d'attaque
    public void attaquer(double positionJoueurX) {
        enAttaque = true;
        directionAttaque = estVersGauche() ? -1 : 1;
        cibleAttaqueX = positionJoueurX;
        animationState = "attack";
    }

    // Termine l'état d'attaque et déclenche une pause
    public void finAttaque() {
        enAttaque = false;
        directionAttaque = 0;
        animationState = "walk";
        pausePoursuite = random.nextInt(60) + 50;
    }

    // Retourne les points de vie actuels
    public int getPointsDeVie() {
        return pointsDeVie;
    }

    // La méthode ...Property() n'est plus nécessaire
    // public IntegerProperty pointsDeVieProperty() {
    //     return pointsDeVie;
    // }
    
    // Retourne le temps de pause restant
    public int getPausePoursuite() {
        return pausePoursuite;
    }

    // Inflige des dégâts au loup et notifie les observateurs
    public void subirDegats(int quantite) {
        this.pointsDeVie = Math.max(0, this.pointsDeVie - quantite);
        notifierObservateurs(TypeChangement.POINTS_DE_VIE);
    }

    // Fait fuir le loup (utilisé par ComportementPassif)
    public void fuirDe(double positionJoueur) {
        if (this.getX() < positionJoueur) {
            this.setX(this.getX() - 10);
        } else {
            this.setX(this.getX() + 10);
        }
    }

    // Change la stratégie de comportement du loup (Strategy Pattern)
    public void setStrategie(ComportementLoup strategie) {
        this.strategie = strategie;
    }

    // Arrête le loup (utilisé par ComportementPassif)
    public void seReposer() {
        this.arreter();
    }


    // Implémentation du Pattern Observer
    
    
    // Ajoute un observateur
    @Override
    public void ajouterObservateur(Observateur obs) {
        sujetObserve.ajouterObservateur(obs);
    }

    // Retire un observateur
    @Override
    public void retirerObservateur(Observateur obs) {
        sujetObserve.retirerObservateur(obs);
    }

    // Notifie les observateurs d'un changement
    @Override
    public void notifierObservateurs(TypeChangement type) {
        sujetObserve.notifierObservateurs(type);
    }
}
