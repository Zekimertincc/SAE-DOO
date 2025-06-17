package universite_paris8.iut.dagnetti.junglequest.modele.ressource;

import universite_paris8.iut.dagnetti.junglequest.modele.farm.Pierre;

public class Roche extends Ressource {
    public Roche(double x, double y) {
        super("Roche", 5, x, y,"Outil", 25);
    }

    @Override
    public Pierre getItemProduit() {
        return new Pierre();
    }

    @Override
    public String getNom() {
        return "Roche";
    }
}

