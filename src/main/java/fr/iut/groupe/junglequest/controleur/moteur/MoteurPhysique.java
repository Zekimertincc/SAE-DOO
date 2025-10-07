package fr.iut.groupe.junglequest.controleur.moteur;

import fr.iut.groupe.junglequest.modele.donnees.ConstantesJeu;
import fr.iut.groupe.junglequest.modele.carte.Carte;
import fr.iut.groupe.junglequest.modele.bloc.TileType;
import fr.iut.groupe.junglequest.modele.personnages.Personnage;

/**
 * Gère les règles physiques élémentaires du jeu :
 * gravité, détection de sol, mise à jour des positions.
 */
public class MoteurPhysique {

    /**
     * Met à jour la physique d'un personnage et indique s'il vient d'atterrir.
     *
     * @param personnage le personnage à mettre à jour
     * @param carte      la carte pour les collisions
     * @return {@code true} si le personnage vient d'atterrir lors de cette mise
     *         à jour
     */
    public boolean mettreAJourPhysique(Personnage personnage, Carte carte, double largeur, double hauteur) {
        // Appliquer la gravité
        personnage.appliquerGravite(ConstantesJeu.GRAVITE, ConstantesJeu.VITESSE_CHUTE_MAX);


        double newX = personnage.getX() + personnage.getVitesseX();
        double newY = personnage.getY() + personnage.getVitesseY();

        // --- Gestion des collisions horizontales ---
        if (personnage.getVitesseX() > 0) { // vers la droite
            int col = (int) ((newX + largeur - 1) / ConstantesJeu.TAILLE_TUILE);
            int top = (int) (personnage.getY() / ConstantesJeu.TAILLE_TUILE);
            int bottom = (int) ((personnage.getY() + hauteur - 1) / ConstantesJeu.TAILLE_TUILE);
            boolean collision = false;
            for (int l = top; l <= bottom && !collision; l++) {
                if (carte.estSolide(l, col)) {
                    newX = col * ConstantesJeu.TAILLE_TUILE - largeur;
                    personnage.setVitesseX(0);
                    collision = true;
                }
            }
        } else if (personnage.getVitesseX() < 0) { // vers la gauche
            int col = (int) (newX / ConstantesJeu.TAILLE_TUILE);
            int top = (int) (personnage.getY() / ConstantesJeu.TAILLE_TUILE);
            int bottom = (int) ((personnage.getY() + hauteur - 1) / ConstantesJeu.TAILLE_TUILE);
            boolean collision = false;
            for (int l = top; l <= bottom && !collision; l++) {
                if (carte.estSolide(l, col)) {
                    newX = (col + 1) * ConstantesJeu.TAILLE_TUILE;
                    personnage.setVitesseX(0);
                    collision = true;
                }
            }
        }

        personnage.setX(newX);

        // --- Gestion des collisions verticales ---
        boolean auSolAvant = personnage.estAuSol();
        boolean auSolApres = false;
        if (personnage.getVitesseY() > 0) { // chute
            int ligneBas = (int) ((newY + hauteur - 1) / ConstantesJeu.TAILLE_TUILE);
            int colGauche = (int) (newX / ConstantesJeu.TAILLE_TUILE);
            int colDroite = (int) ((newX + largeur - 1) / ConstantesJeu.TAILLE_TUILE);
            boolean collision = false;
            for (int c = colGauche; c <= colDroite && !collision; c++) {
                int id = carte.getValeurTuile(ligneBas, c);
                if (carte.estSolide(ligneBas, c) && TileType.fromId(id) != TileType.ARBRE) {
                    newY = ligneBas * ConstantesJeu.TAILLE_TUILE - hauteur;
                    personnage.setVitesseY(0);
                    auSolApres = true;
                    collision = true;
                }
            }
        } else if (personnage.getVitesseY() < 0) { // saut
            int ligneHaut = (int) (newY / ConstantesJeu.TAILLE_TUILE);
            int colGauche = (int) (newX / ConstantesJeu.TAILLE_TUILE);
            int colDroite = (int) ((newX + largeur - 1) / ConstantesJeu.TAILLE_TUILE);
            boolean collision = false;
            for (int c = colGauche; c <= colDroite && !collision; c++) {
                int id = carte.getValeurTuile(ligneHaut, c);
                if (carte.estSolide(ligneHaut, c) && TileType.fromId(id) != TileType.ARBRE) {
                    newY = (ligneHaut + 1) * ConstantesJeu.TAILLE_TUILE;
                    personnage.setVitesseY(0);
                    collision = true;
                }
            }
        } else {
            // Lorsque la vitesse verticale est nulle, on vérifie la présence
            // d'un bloc solide directement sous le personnage. L'ancienne
            // implémentation utilisait "hauteur - 1" pour calculer la ligne
            // inférieure, ce qui provoquait un décalage d'une tuile : le joueur
            // n'était plus considéré comme au sol lorsque son bas se trouvait
            // exactement sur la bordure d'une tuile. En conséquence, la méthode
            // signalait un atterrissage à chaque frame et l'animation
            // d'atterrissage tournait en boucle. On calcule donc ici la ligne en
            // utilisant "hauteur" pour tester correctement la tuile située juste
            // en dessous.
            int ligneBas = (int) ((newY + hauteur) / ConstantesJeu.TAILLE_TUILE);
            int colGauche = (int) (newX / ConstantesJeu.TAILLE_TUILE);
            int colDroite = (int) ((newX + largeur - 1) / ConstantesJeu.TAILLE_TUILE);
            boolean collision = false;
            for (int c = colGauche; c <= colDroite && !collision; c++) {
                int id = carte.getValeurTuile(ligneBas, c);
                if (carte.estSolide(ligneBas, c) && TileType.fromId(id) != TileType.ARBRE) {
                    auSolApres = true;
                    collision = true;
                }
            }
        }
        personnage.setY(newY);
        personnage.setEstAuSol(auSolApres);
        return !auSolAvant && auSolApres;
    }
}
