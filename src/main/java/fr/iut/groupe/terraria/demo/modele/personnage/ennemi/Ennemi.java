package fr.iut.groupe.terraria.demo.modele.personnage.ennemi;

import fr.iut.groupe.terraria.demo.modele.Ciblable;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import fr.iut.groupe.terraria.demo.modele.personnage.Personnage;
import fr.iut.groupe.terraria.demo.modele.monde.Maths;

public abstract class Ennemi extends Personnage implements Ciblable {
    protected int distanceAttaque;

    public Ennemi(double x, double y, double vitesseX, double vitesseY, int vieMax, int degats, int distanceAttaque) {
        super(x, y, vitesseX, vitesseY, vieMax, vieMax, degats);
        this.distanceAttaque = distanceAttaque;
    }

    public abstract void comportement(Joueur joueur);

    public void mettreAJour(Joueur joueur) {
        if (!this.estMort() && estProcheDe(joueur)) {
            seDeplacerVers(joueur);
            attaquer(joueur);
        }
        comportement(joueur);
    }

    public void attaquer(Joueur joueur) {
        if (estProcheAttaque(joueur)) {
            joueur.subirDegats(this.degats);
        }
    }

    public boolean estProcheAttaque(Joueur joueur) {
        double d = Maths.distance(joueur.getX(), joueur.getY(), this.getX(), this.getY());
        return d < getDistanceAttaque();
    }

    public void seDeplacerVers(Joueur joueur) {
        if (joueur.getX() > this.getX()) {
            this.setX(this.getX() + this.vitesseX);
        } else if (joueur.getX() < this.getX()) {
            this.setX(this.getX() - this.vitesseX);
        }
    }

    @Override
    public String getTypeCible() {
        return "Ennemi";
    }

    public int getDistanceAttaque() {
        return distanceAttaque;
    }

    public boolean estProcheDe(Joueur joueur) {
        return Maths.distance(joueur.getX(), joueur.getY(), this.getX(), this.getY()) < 250;
    }
}
