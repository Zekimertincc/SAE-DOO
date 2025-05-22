package fr.iut.groupe.terraria.demo.modele.ressource;

public class Arbre extends Ressource {
    public Arbre(double x, double y) {
        super(x, y);
    }

    @Override
    public String getNom() {
        return "Arbre";
    }
}
