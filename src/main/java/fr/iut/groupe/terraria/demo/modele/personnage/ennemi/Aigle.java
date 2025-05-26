package fr.iut.groupe.terraria.demo.modele.personnage.ennemi;

public class Aigle extends Ennemi {
    public Aigle(double x, double y) {
        super(x, y, 20, 2, 100);
    }


    public String getNom() {
        return "Aigle";
    }
}
