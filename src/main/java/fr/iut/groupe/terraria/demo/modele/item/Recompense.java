/*package fr.iut.groupe.terraria.demo.modele.item;

import fr.iut.groupe.terraria.demo.modele.Inventaire;

import java.util.ArrayList;
import java.util.Random;

public class Recompense {
    private ArrayList<Item> listContenu;
    protected Inventaire inventaire;

    public Recompense(Inventaire inventaire) {
        this.listContenu = new ArrayList<>();
        this.inventaire = inventaire;
    }

    // choisir un item dans le coffre
    public Item randomItem() {
        if (listContenu.isEmpty()) {
            return null;
        }

        Random rand = new Random();
        return listContenu.get(rand.nextInt(listContenu.size()));
    }
}
*/