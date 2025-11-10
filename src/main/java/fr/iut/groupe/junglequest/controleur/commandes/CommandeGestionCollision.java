package fr.iut.groupe.junglequest.controleur.commandes;

import fr.iut.groupe.junglequest.modele.personnages.Joueur;
import fr.iut.groupe.junglequest.modele.personnages.Loup;
import fr.iut.groupe.junglequest.modele.donnees.ConstantesJeu;
import javafx.scene.image.ImageView;

/**
 * Commande pour gérer les collisions et les dégâts entre le joueur et le loup.
 */
public class CommandeGestionCollision implements Commande {
    private final Joueur joueur;
    private final Loup loup;
    private final double distanceLoupJoueur;
    private final ImageView vueJoueur;
    private final ImageView vueLoup;
    private final int delaiDegats;
    
    public CommandeGestionCollision(Joueur joueur, Loup loup, double distanceLoupJoueur,
                                  ImageView vueJoueur, ImageView vueLoup, int delaiDegats) {
        this.joueur = joueur;
        this.loup = loup;
        this.distanceLoupJoueur = distanceLoupJoueur;
        this.vueJoueur = vueJoueur;
        this.vueLoup = vueLoup;
        this.delaiDegats = delaiDegats;
    }

    @Override
    public void executer() {
        boolean collision = distanceLoupJoueur < 20 &&
            joueur.getX() < loup.getX() + vueLoup.getFitWidth() &&
            joueur.getX() + vueJoueur.getFitWidth() > loup.getX() &&
            joueur.getY() < loup.getY() + vueLoup.getFitHeight() &&
            joueur.getY() + vueJoueur.getFitHeight() > loup.getY();

        if (!loup.estMort() && collision && delaiDegats == 0 && 
            !loup.estEnAttaque() && !joueur.isBouclierActif()) {
            joueur.encaisserDegats(loup.getDegats());
            loup.attaquer(joueur.getX());
        }
    }
}