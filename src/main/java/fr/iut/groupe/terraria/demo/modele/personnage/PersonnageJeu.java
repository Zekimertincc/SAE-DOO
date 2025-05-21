package fr.iut.groupe.terraria.demo.modele.personnage;

import fr.iut.groupe.terraria.demo.modele.monde.Maths;

public abstract class PersonnageJeu extends Personnage {
    protected int distanceDetection;
    public PersonnageJeu(double x, double y, int vie, int vieMax, int degats, int distanceDetection) {
        super(x, y, vie, vieMax, degats);
        this.distanceDetection = distanceDetection;
    }

    // retourne true si un enemi est proche du joueur (distance de 20)
    public boolean estProcheDe(Joueur joueur) {
        double d = Maths.distance(joueur.getX(), joueur.getY(), this.getX(), this.getY());
        return d < getDistanceDetection();
    }

    public int getDistanceDetection() {
        return distanceDetection;
    }
}
