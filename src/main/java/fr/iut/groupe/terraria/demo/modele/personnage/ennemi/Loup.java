package fr.iut.groupe.terraria.demo.modele.personnage.ennemi;

import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;

public class Loup extends Ennemi {
    public Loup(double x, double y) {
        super(x, y, 2, 0, 15, 1, 5);
    }

    /**
     * Si le loup a moins de 33% de ses PV, il s'enfuit de 2px vers la gauche.
     */
    @Override
    public void comportement(Joueur joueur) {
        if (this.getVie() < this.getVieMax() / 3) {
            this.setX(this.getX() - 2);  // binding-compatible
        }
    }

    @Override
    public String getNom() {
        return "Loup";
    }
}
