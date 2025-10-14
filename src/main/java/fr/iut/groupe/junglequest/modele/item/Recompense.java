package fr.iut.groupe.junglequest.modele.item;

import fr.iut.groupe.junglequest.modele.item.Inventaire;

import java.util.ArrayList;
import java.util.Random;

public class Recompense {
    private ArrayList<Item> listContenu;

    public Recompense() {
        this.listContenu = new ArrayList<>();
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
