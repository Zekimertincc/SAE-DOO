package fr.iut.groupe.terraria.demo.controller.moteur;

import fr.iut.groupe.terraria.demo.modele.carte.Carte;
import fr.iut.groupe.terraria.demo.modele.donnees.ConstantesJeu;
import fr.iut.groupe.terraria.demo.modele.personnage.Personnage;

/**
 * Gère les règles physiques élémentaires du jeu :
 * gravité, détection de sol, mise à jour des positions.
 */
public class MoteurPhysique {

    /**
     * Applique la physique à un personnage : chute, collisions, position.
     *
     * @param personnage Le personnage à mettre à jour (ex. Joueur, Ennemi, etc.)
     * @param carte      La carte contenant les tuiles solides
     * @param decalageX  Le décalage horizontal actuel lié au scrolling
     */
    public void mettreAJourPhysique(Personnage personnage, Carte carte, double decalageX) {
        // Appliquer la gravité uniquement si le personnage est en l'air
        personnage.appliquerGravite(ConstantesJeu.GRAVITE, ConstantesJeu.VITESSE_CHUTE_MAX);

        // Coordonnées des pieds du personnage (centre bas du sprite)
        double piedX = personnage.getX() + personnage.getSprite().getFitWidth() / 2 + decalageX;
        double piedY = personnage.getY() + personnage.getSprite().getFitHeight();

        int colonne = (int) (piedX / ConstantesJeu.TAILLE_TUILE);
        int ligne = (int) (piedY / ConstantesJeu.TAILLE_TUILE);

        // Vérifier si le sol est solide sous les pieds
        if (carte.estSolide(ligne, colonne)) {
            double ySol = ligne * ConstantesJeu.TAILLE_TUILE;

            // Si le personnage tombe et dépasse le sol : poser au sol
            if (personnage.getVitesseY() >= 0 && piedY >= ySol) {
                personnage.poserAuSol(ySol - personnage.getSprite().getFitHeight());
            }
        } else {
            personnage.setEstAuSol(false);
        }


        personnage.mettreAJourPosition();
    }
}
