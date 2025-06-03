package fr.iut.groupe.terraria.demo.modele.zone;


import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;

public class Ralentissement extends Zone {
    public Ralentissement(double x, double y, double largeur, double hauteur) {
        super(x, y, largeur, hauteur);
    }

    // ralentissement du personnage dans cette zone (elle ne fera pas de degats)
    public void appliquerEffet(Joueur joueur) {
        if (estJoueurDansZone(joueur)) {
            joueur.setVitesseX(0.5);
        } else {
            joueur.setVitesseX(1.5);
        }
    }
}
