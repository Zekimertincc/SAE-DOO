package universite_paris8.iut.dagnetti.junglequest.modele.craft;

import universite_paris8.iut.dagnetti.junglequest.modele.Inventaire;
import universite_paris8.iut.dagnetti.junglequest.modele.item.Item;
import universite_paris8.iut.dagnetti.junglequest.modele.item.equipement.Equipement;

import java.util.ArrayList;

public class Craft {
    protected Inventaire inventaire;

    public Craft(Inventaire inventaire) {
        this.inventaire = inventaire;
    }
}
