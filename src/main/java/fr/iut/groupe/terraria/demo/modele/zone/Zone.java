package fr.iut.groupe.terraria.demo.modele.zone;

import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;

public abstract class Zone {
    private double x, y;
    private double largeur, hauteur;

    public Zone(double x, double y, double largeur, double hauteur) {
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    public boolean estJoueurDansZone(Joueur joueur) {
        double jx = joueur.getX();
        double jy = joueur.getY();
        return (jx >= x && jx <= x + largeur) && (jy >= y && jy <= y + hauteur);
    }

    public abstract void appliquerEffet(Joueur joueur);
}

