package fr.iut.groupe.terraria.demo.modele;

import fr.iut.groupe.terraria.demo.modele.item.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventaire {
    private HashMap<String, Integer> mapItems; // le bois par exemple avec sa quantité

    public Inventaire() {
        this.mapItems = new HashMap<>();
    }

    public boolean ajouterItem(Item item) {
        boolean ajouter = false;
        int actuel = mapItems.getOrDefault(item.getNom(), 0);

        if (actuel < item.getQuantiteMax()) {
            mapItems.put(item.getNom(), actuel + 1);
            ajouter = true;
        }
        return ajouter;
    }
    public boolean retirerItem(String nom) {
        boolean retirer = false; 
        int actuel = mapItems.getOrDefault(nom, 0);
        if (actuel > 0) {
            mapItems.put(nom, actuel - 1);
            retirer = true;
        }
        return retirer;
    }

    public boolean retirerItem(String nom, int quantite) {
        boolean retirer = false;
        int actuel = mapItems.getOrDefault(nom, 0);
        if (actuel >= quantite) {
            mapItems.put(nom, actuel - quantite);
            retirer = true;
        }
        return retirer;
    }


    public HashMap<String, Integer> getMapItems() {
        return mapItems;
    }
}