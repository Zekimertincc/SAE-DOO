package fr.iut.groupe.terraria.demo.modele.personnage.ennemi;

import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;

public class Aigle extends Ennemi {
    public Aigle(double x, double y) {
        super(x, y, 20, 2, 100);
    }

    // quand le loup a moins de 25% de ses hp max il lache un pique qui met la vie actuelle du joueur à 1 coeur puis meurt.
    @Override
    public void comportement(Joueur joueur) {
        if (this.getVie() < this.getVieMax() / 4) {
            joueur.mettreAPV(1);
            this.vie = 0;
        }
    }
    @Override
    public String getNom() {
        return "Aigle";
    }
}