package fr.iut.groupe.terraria.demo.modele.personnage.ennemi;

import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;

public class Gorille extends Ennemi {

    public Gorille(double x, double y) {
        super(x, y, 30, 2, 10);
    }

    @Override
    public String getNom() {
        return "Gorille";
    }
}
