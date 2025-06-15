package universite_paris8.iut.dagnetti.junglequest.modele.personnages;

import javafx.scene.image.ImageView;

/**
 * Représente un ennemi de type loup.
 */
public class Loup extends Personnage {

    private final int degats;
    /**
     * Rayon de détection du joueur (en pixels).
     */
    private final double zoneDetection = 110;

    /**
     * Vitesse horizontale du loup.
     */
    private final double vitesse = 1.3;


    public Loup(ImageView sprite, double x, double y, int degats) {
        super(sprite, x, y);
        this.degats = degats;
    }
    /**
     * Met à jour le déplacement du loup en fonction de la position du joueur.
     */
    public void mettreAJourIA(Joueur joueur) {
        double distance = joueur.getX() - this.x;
        if (Math.abs(distance) <= zoneDetection) {
            if (distance > 0) {
                deplacerDroite(vitesse);
            } else {
                deplacerGauche(vitesse);
            }
        } else {
            arreter();
        }
    }

    /**
     * Dégâts infligés au joueur lors d'un contact.
     */
    public int getDegats() {
        return degats;
    }
}