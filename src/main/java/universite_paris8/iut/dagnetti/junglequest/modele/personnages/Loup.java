package universite_paris8.iut.dagnetti.junglequest.modele.personnages;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Random;
import universite_paris8.iut.dagnetti.junglequest.modele.donnees.ConstantesJeu;

/**
 * Représente un ennemi de type loup.
 */
public class Loup extends Personnage {

    private final int degats;
    private int pointsDeVie;
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

    private boolean enAttaque = false;

    /**
     * Direction prise lors du lancement d'une attaque : -1 pour la gauche,
     * 1 pour la droite.
     */
    private int directionAttaque = 0;

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


    public Loup(ImageView sprite, Image walkImage, Image runImage, Image attackImage, double x, double y, int degats) {
        super(sprite, x, y);
        this.walkImage = walkImage;
        this.runImage = runImage;
        this.attackImage = attackImage;
        this.degats = degats;
        this.pointsDeVie = ConstantesJeu.VIE_MAX_LOUP;
    }
    /**
     * Met à jour le déplacement du loup en fonction de la position du joueur.
     */
    public void mettreAJourIA(Joueur joueur) {
        double distance = joueur.getX() - this.x;
        // Si une attaque est en cours, le loup continue son mouvement dans la
        // direction initiale, sans ajuster sa trajectoire sur le joueur.
        if (enAttaque) {
            if (directionAttaque >= 0) {
                deplacerDroite(vitesseCourse);
            } else {
                deplacerGauche(vitesseCourse);
            }
            return;
        }

        // Le joueur est à portée d'attaque : le loup s'arrête et attend
        if (Math.abs(distance) <= ConstantesJeu.DISTANCE_ARRET_LOUP) {
            arreter();
            getSprite().setImage(walkImage);
            if (delaiAvantAttaque > 0) {
                delaiAvantAttaque--;
            } else {
                attaquer();
                delaiAvantAttaque = ConstantesJeu.DELAI_AVANT_ATTAQUE_LOUP;
            }
            return;
        }

        // Le joueur est détecté mais pas encore à portée : poursuite
        if (Math.abs(distance) <= zoneDetection) {
            if (pausePoursuite > 0) {
                pausePoursuite--;
                arreter();
                getSprite().setImage(walkImage);
            } else {
                if (random.nextDouble() < 0.02) {
                    pausePoursuite = random.nextInt(60) + 30;
                    arreter();
                    getSprite().setImage(walkImage);
                } else {
                    getSprite().setImage(runImage);
                    if (distance > 0) {
                        deplacerDroite(vitesseCourse);
                    } else {
                        deplacerGauche(vitesseCourse);
                    }
                }
            }
            delaiAvantAttaque = ConstantesJeu.DELAI_AVANT_ATTAQUE_LOUP;
        } else {
            // Hors de portée : comportement aléatoire de marche
            getSprite().setImage(walkImage);
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

    /**
     * Dégâts infligés au joueur lors d'un contact.
     */
    public int getDegats() {
        return degats;
    }
    public Image getWalkImage() { return walkImage; }
    public Image getRunImage() { return runImage; }
    public Image getAttackImage() { return attackImage; }

    public boolean estEnAttaque() { return enAttaque; }

    public void attaquer() {
        enAttaque = true;
        // On enregistre la direction actuelle pour la conserver durant
        // toute la séquence d'attaque.
        directionAttaque = versGauche ? -1 : 1;
        getSprite().setImage(attackImage);
    }

    public void finAttaque() {
        enAttaque = false;
        directionAttaque = 0;
        getSprite().setImage(walkImage);
    }
    public int getPointsDeVie() {
        return pointsDeVie;
    }

    public void subirDegats(int quantite) {
        pointsDeVie -= quantite;
        if (pointsDeVie < 0) {
            pointsDeVie = 0;
        }
    }
}