package fr.iut.groupe.terraria.demo.modele.personnage.ennemi;

import fr.iut.groupe.terraria.demo.modele.Ciblable;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import fr.iut.groupe.terraria.demo.modele.personnage.PersonnageJeu;
import fr.iut.groupe.terraria.demo.modele.monde.Maths;

public abstract class Ennemi extends PersonnageJeu implements Ciblable {
    protected int distanceAttaque;
    public Ennemi(double x, double y, int vieMax, int degats, int distanceAttaque) {
        super(x, y, vieMax, vieMax, degats, 30);
        this.distanceAttaque = distanceAttaque;
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

    /* avance de 1 x vers le joueur
        Cette fonction est à utiliser que quand le personnage est dans la zone d'un ou plusieurs enemis
     */
    public void seDeplacerVers(Joueur joueur) {
        if (joueur.getX() > this.x) {
            this.x += 1;
        } else if (joueur.getX() < this.x) {
            this.x -= 1;
        }
    }
    @Override
    public void subirDegats(int degats) {
        this.vie -= degats;
        if (estMort()) {
            this.vie = 0;
        }
    }

    // --------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public String getTypeCible() {
        return "Ennemi";
    }

    public int getDistanceAttaque() {
        return distanceAttaque;
    }
}
