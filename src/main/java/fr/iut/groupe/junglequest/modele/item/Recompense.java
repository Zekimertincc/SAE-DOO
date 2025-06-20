package fr.iut.groupe.junglequest.modele.item;

import fr.iut.groupe.junglequest.modele.item.Inventaire;

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
