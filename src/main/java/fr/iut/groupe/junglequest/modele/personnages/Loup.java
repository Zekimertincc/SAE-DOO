package fr.iut.groupe.junglequest.modele.personnages;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.Random;
import fr.iut.groupe.junglequest.modele.donnees.ConstantesJeu;
import fr.iut.groupe.junglequest.modele.monde.AStar;
import fr.iut.groupe.junglequest.modele.carte.Carte;
import fr.iut.groupe.junglequest.modele.personnages.strategies.*;

/**
 * Représente un ennemi de type loup.
 * 
 * Architecture MVC:
 * - Pure Model - NO UI elements (no ImageView, no Image)
 * - Contains only game logic and state
 * - Animation state is represented as String (not Image)
 * - View components handle actual image rendering
 */
public class Loup extends Personnage {

    private final int degats;
    private final IntegerProperty pointsDeVie = new SimpleIntegerProperty();
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
    
    /**
     * Rayon de détection du joueur (en pixels).
     */
    private final double zoneDetection = 450;

    /**
     * Vitesse horizontale du loup.
     */
    private final double vitesseMarche = 0.8;
    private final double vitesseCourse = 1.3;
    
    // Animation state (Model representation - View will use this to select Image)
    private String animationState = "walk";
    private boolean enAttaque = false;
    /**
     * Direction prise lors du lancement d'une attaque : -1 pour la gauche,
     * 1 pour la droite.
     */
    private int directionAttaque = 0;
    /**
     * Position visée lors du début de l'attaque. Le loup continue de se
     * déplacer jusqu'à atteindre ce point afin d'éviter de dépasser un
     * joueur immobile.
     */
    private double cibleAttaqueX = 0;
    /**
     * Compteur gérant le temps d'attente avant une nouvelle attaque lorsque
     * le joueur est à portée.
     */
    private int delaiAvantAttaque = ConstantesJeu.DELAI_AVANT_ATTAQUE_LOUP;
    private final Random random = new Random();
    private int dureeAction = 0;
    /**
     * Temps restant d'une éventuelle pause dans la poursuite du joueur.
     */
    private int pausePoursuite = 0;

    private ComportementLoup strategie;
    
    /**
     * Constructeur du loup avec toutes ses caractéristiques
     * @param x Position X initiale
     * @param y Position Y initiale
     * @param largeur Largeur du sprite
     * @param hauteur Hauteur du sprite
     * @param degats Dégâts infligés
     */
    public Loup(double x, double y, double largeur, double hauteur, int degats) {
        super(x, y, largeur, hauteur);
        this.degats = degats;
        this.pointsDeVie.set(ConstantesJeu.VIE_MAX_LOUP);
        this.strategie = new ComportementAgressif();
        this.animationState = "walk";
    }
    public void setDureeAction(int duree) {
        this.dureeAction = duree;
    }

    /**
     * Met à jour le déplacement du loup en fonction de la position du joueur.
     */
    public void mettreAJourIA(Joueur joueur, Carte carte) {
        if (strategie != null) {
            strategie.mettreAJour(this, joueur, carte);
        }
    }

    public void gererPause() {
        pausePoursuite--;
        arreter();
        animationState = "walk";
    }

    public void gererInteractionAvecJoueur(double distance, Joueur joueur, Carte carte) {
        if (Math.abs(distance) <= ConstantesJeu.DISTANCE_ARRET_LOUP) {
            gererApprocheAttaque(distance, joueur);
        } else {
            gererDeplacement(distance, joueur, carte);
        }
    }
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

    /**
     * Dégâts infligés au joueur lors d'un contact.
     */
    public int getDegats() {
        return degats;
    }

    public boolean estEnAttaque() { return enAttaque; }

    public void attaquer(double positionJoueurX) {
        enAttaque = true;
        // On enregistre la direction actuelle pour la conserver durant
        // toute la séquence d'attaque.
        directionAttaque = estVersGauche() ? -1 : 1;
        cibleAttaqueX = positionJoueurX;
        animationState = "attack";
    }

    public void finAttaque() {
        enAttaque = false;
        directionAttaque = 0;
        animationState = "walk";
        pausePoursuite = random.nextInt(60) + 50;
    }
    public int getPointsDeVie() {
        return pointsDeVie.get();
    }

    public IntegerProperty pointsDeVieProperty() {
        return pointsDeVie;
    }
    public int getPausePoursuite() {
        return pausePoursuite;
    }

    public void subirDegats(int quantite) {
        pointsDeVie.set(Math.max(0, pointsDeVie.get() - quantite));
    }

    public void fuirDe(double positionJoueur) {
        // Exemple simple : s'éloigne du joueur
        if (this.getX() < positionJoueur) {
            this.setX(this.getX() - 10); // se déplace vers la gauche
        } else {
            this.setX(this.getX() + 10); // se déplace vers la droite
        }
    }

    /**
     * Change la stratégie de comportement du loup (Strategy Pattern)
     * @param strategie La nouvelle stratégie à appliquer
     */
    public void setStrategie(ComportementLoup strategie) {
        this.strategie = strategie;
    }

    public void seReposer() {
        this.arreter(); // stoppe le déplacement
    }

}