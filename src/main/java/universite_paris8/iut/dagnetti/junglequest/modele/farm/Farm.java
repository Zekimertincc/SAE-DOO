package universite_paris8.iut.dagnetti.junglequest.modele.farm;

import universite_paris8.iut.dagnetti.junglequest.modele.item.Inventaire;
import universite_paris8.iut.dagnetti.junglequest.modele.item.Item;

public abstract class Farm extends Item {
    private int quantiteMax; // 50 max par type dans l'inventaire

    public Farm(String nom) {
        super(nom);
        this.quantiteMax = 10;
    }

    @Override
    public int getQuantiteMax() {
        return quantiteMax;
    }

}
