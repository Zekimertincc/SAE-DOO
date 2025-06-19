package universite_paris8.iut.dagnetti.junglequest.modele.personnages;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import java.util.Random;
import universite_paris8.iut.dagnetti.junglequest.modele.donnees.ConstantesJeu;
import universite_paris8.iut.dagnetti.junglequest.modele.monde.AStar;
import universite_paris8.iut.dagnetti.junglequest.modele.carte.Carte;

/**
 * Représente un ennemi de type loup.
 */
public class Loup extends Personnage {

    private final int degats;
    private final IntegerProperty pointsDeVie = new SimpleIntegerProperty();
    /**
     * Rayon de détection du joueur (en pixels).
     */
    private final double zoneDetection = 450;

    /**
     * Vitesse horizontale du loup.
     */
    private final double vitesseMarche = 0.8;
    private final double vitesseCourse = 1.3;

    private final Image walkImage;
    private final Image runImage;
    private final Image attackImage;
    private Image currentImage;

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


    public Loup(double x, double y, double largeur, double hauteur,
                Image walkImage, Image runImage, Image attackImage, int degats) {
        super(x, y, largeur, hauteur);
        this.walkImage = walkImage;
        this.runImage = runImage;
        this.attackImage = attackImage;
        this.currentImage = walkImage;
        this.degats = degats;
        this.pointsDeVie.set(ConstantesJeu.VIE_MAX_LOUP);

    }
    /**
     * Met à jour le déplacement du loup en fonction de la position du joueur.
     */
    public void mettreAJourIA(Joueur joueur, Carte carte) {
        double distance = joueur.getX() - getX();

        if (enAttaque) {
            poursuivreAttaque();
        } else if (pausePoursuite > 0) {
            pausePoursuite--;
            arreter();
            currentImage = walkImage;
        } else if (Math.abs(distance) <= ConstantesJeu.DISTANCE_ARRET_LOUP) {
            gererApprocheAttaque(distance, joueur);
        } else {
            gererDeplacement(distance, joueur, carte);
        }
    }

    private void poursuivreAttaque() {
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

    private void gererDeplacement(double distance, Joueur joueur, universite_paris8.iut.dagnetti.junglequest.modele.carte.Carte carte) {
        if (Math.abs(distance) <= zoneDetection) {
            suivreChemin(joueur, carte);
            delaiAvantAttaque = ConstantesJeu.DELAI_AVANT_ATTAQUE_LOUP;
        } else {
            currentImage = walkImage;
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

    private void suivreChemin(Joueur joueur, universite_paris8.iut.dagnetti.junglequest.modele.carte.Carte carte) {
        int startX = (int) (getX() / ConstantesJeu.TAILLE_TUILE);
        int startY = (int) (getY() / ConstantesJeu.TAILLE_TUILE);
        int goalX = (int) (joueur.getX() / ConstantesJeu.TAILLE_TUILE);
        int goalY = (int) (joueur.getY() / ConstantesJeu.TAILLE_TUILE);

        java.util.List<int[]> chemin = AStar.chercherChemin(carte, startX, startY, goalX, goalY);
        if (chemin.size() > 1) {
            int[] next = chemin.get(1);
            double cibleX = next[0] * ConstantesJeu.TAILLE_TUILE;
            currentImage = runImage;
            if (cibleX > getX()) {
                deplacerDroite(vitesseCourse);
            } else if (cibleX < getX()) {
                deplacerGauche(vitesseCourse);
            } else {
                arreter();
            }
        } else {
            currentImage = runImage;
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
    public Image getWalkImage() { return walkImage; }
    public Image getRunImage() { return runImage; }
    public Image getAttackImage() { return attackImage; }
    public Image getCurrentImage() { return currentImage; }

    public boolean estEnAttaque() { return enAttaque; }

    public void attaquer(double positionJoueurX) {
        enAttaque = true;
        // On enregistre la direction actuelle pour la conserver durant
        // toute la séquence d'attaque.
        directionAttaque = estVersGauche() ? -1 : 1;
        cibleAttaqueX = positionJoueurX;
        currentImage = attackImage;
    }

    public void finAttaque() {
        enAttaque = false;
        directionAttaque = 0;
        currentImage = walkImage;
        pausePoursuite = random.nextInt(60) + 50;
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
}