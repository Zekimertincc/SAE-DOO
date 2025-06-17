package universite_paris8.iut.dagnetti.junglequest.modele.ressource;

import universite_paris8.iut.dagnetti.junglequest.modele.farm.Bois;

public class Arbre extends Ressource {
    public Arbre(double x, double y) {
        super("Arbre", 5, x, y,"Outil", 20);
    }

    @Override
    public Bois getItemProduit() {
        return new Bois();
    }

    @Override
    public String getNom() {
        return "Arbre";
    }
}
