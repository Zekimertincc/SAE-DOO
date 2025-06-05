/*package fr.iut.groupe.terraria.demo.modele.personnage.ennemi;

import fr.iut.groupe.terraria.demo.modele.Ciblable;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import fr.iut.groupe.terraria.demo.modele.personnage.PersonnageJeu;
import fr.iut.groupe.terraria.demo.modele.monde.Maths;

public abstract class Ennemi extends PersonnageJeu implements Ciblable {
    protected int distanceAttaque;
    public Ennemi(double x, double y, double vitesseX, double vitesseY, int vieMax, int degats, int distanceAttaque) {
        super(x, y, vitesseX, vitesseY, vieMax, vieMax, degats, 30);
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
    // pour attaquer (utiliser dans monde) si truc alors l'ennemi attaque (dans la class monde)
    public boolean estProcheAttaque(Joueur joueur) {
        double d = Maths.distance(joueur.getX(), joueur.getY(), this.getX(), this.getY());
        return d < getDistanceAttaque();
    }

    /* avance d'une vitesse vitesseX vers le joueur
        Cette fonction est à utiliser que quand le personnage est dans la zone d'un ou plusieurs enemis
     */
/*
    public void seDeplacerVers(Joueur joueur) {
        if (joueur.getX() > this.x) {
            this.x += this.vitesseX;
        } else if (joueur.getX() < this.x) {
            this.x -= this.vitesseX;
        }
    }


    @Override
    public String getTypeCible() {
        return "Ennemi";
    }
    // -----------------------------------/*
    public int getDistanceAttaque() {
        return distanceAttaque;
    }
}
*/