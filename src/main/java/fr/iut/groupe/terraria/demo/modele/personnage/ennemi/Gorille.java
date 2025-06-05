package fr.iut.groupe.terraria.demo.modele.personnage.ennemi;

import fr.iut.groupe.terraria.demo.modele.monde.Maths;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;

public class Gorille extends Ennemi {

    public Gorille(double x, double y) {
        super(x, y, 1, 0, 30, 2, 10);
    }

    /**
     * Si le gorille a moins de 50% de vie, il saute de 3px vers le joueur.
     */
    @Override
    public void comportement(Joueur joueur) {
        if (this.getVie() < this.getVieMax() / 2 &&
                Maths.distance(this.getX(), this.getY(), joueur.getX(), joueur.getY()) > 5) {

            if (joueur.getX() > this.getX()) {
                this.setX(this.getX() + 3);
            } else {
                this.setX(this.getX() - 3);
            }
        }
    }

    @Override
    public String getNom() {
        return "Gorille";
    }
}
