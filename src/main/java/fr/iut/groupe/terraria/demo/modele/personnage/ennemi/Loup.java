package fr.iut.groupe.terraria.demo.modele.personnage.ennemi;

import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;

public class Loup extends Ennemi {
    public Loup(double x, double y) {
        super(x, y, 2, 0, 15, 1,5);
    }

    // quand le loup a moins de 33% de ses hp max il s'enfuit de 2px vers la droite
    @Override
    public void comportement(Joueur joueur) {
        if (this.getVie() < this.getVieMax() / 3) {
            this.x -= 2;
        }
    }

    @Override
    public String getNom() {
        return "Loup";
    }
}