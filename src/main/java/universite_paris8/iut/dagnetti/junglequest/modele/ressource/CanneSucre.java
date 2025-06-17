package universite_paris8.iut.dagnetti.junglequest.modele.ressource;

import universite_paris8.iut.dagnetti.junglequest.modele.farm.File;

public class CanneSucre extends Ressource {
    public CanneSucre(double x, double y) {
        super("CanneSucre", 5, x, y, "Outil", 15);
    }

    @Override
    public File getItemProduit() {
        return new File();
    }

    @Override
    public String getNom() {
        return "CanneSucre";
    }
}
