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
    private final double zoneDetection = 250;

    /**
     * Vitesse horizontale du loup.
     */
    private final double vitesseMarche = 0.8;
    private final double vitesseCourse = 1.3;

    private final Image walkImage;
    private final Image runImage;
    private final Image attackImage;

    private boolean enAttaque = false;

    private final Random random = new Random();
    private int dureeAction = 0;


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
        if (enAttaque) {
            arreter();
            return;
        }
        double distance = joueur.getX() - this.x;
        if (Math.abs(distance) <= ConstantesJeu.DISTANCE_ARRET_LOUP) {
            arreter();
        } else if (Math.abs(distance) <= zoneDetection) {
            getSprite().setImage(runImage);
            if (distance > 0) {
                deplacerDroite(vitesseCourse);
            } else {
                deplacerGauche(vitesseCourse);
            }
        } else {
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
        getSprite().setImage(attackImage);
    }

    public void finAttaque() {
        enAttaque = false;
        getSprite().setImage(walkImage);
        arreter();
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