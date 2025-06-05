package fr.iut.groupe.terraria.demo.modele.personnage.ennemi;

import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;

public class Aigle extends Ennemi {
    public Aigle(double x, double y) {
        super(x, y, 1.5, 0, 20, 2, 100);
    }

    /**
     * Si l’aigle a moins de 25% de PV, il attaque violemment :
     * met le joueur à 1 cœur puis meurt.
     */
    @Override
    public void comportement(Joueur joueur) {
        if (this.getVie() < this.getVieMax() / 4) {
            joueur.mettreAPV(1);
            this.vie = 0; // meurt après avoir attaqué
        }
    }

    @Override
    public String getNom() {
        return "Aigle";
    }
}
