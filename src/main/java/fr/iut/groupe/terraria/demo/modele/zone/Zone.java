package fr.iut.groupe.terraria.demo.modele.zone;

import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;

public class Zone {
    private double x, y;
    private double largeur, hauteur;
    private int degats; // en points de vie à retirer

    public Zone(double x, double y, double largeur, double hauteur, int degats) {
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.degats = degats;
    }

    public boolean estJoueurDansZone(Joueur joueur) {
        double jx = joueur.getX();
        double jy = joueur.getY();
        return (jx >= x && jx <= x + largeur) && (jy >= y && jy <= y + hauteur);
    }

    public void appliquerEffet(Joueur joueur) {
        if (estJoueurDansZone(joueur)) {
            joueur.subirDegats(degats);
        }
    }
}

