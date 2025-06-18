package fr.iut.groupe.terraria.demo.modele;

import fr.iut.groupe.terraria.demo.modele.item.Item;


import java.util.HashMap;

public class Inventaire {
    private HashMap<String, Integer> mapItems;

    public Inventaire() {
        this.mapItems = new HashMap<>();
    }

    public boolean ajouterItem(Item item) {
        return ajouterItem(item, 1);
    }

    public boolean ajouterItem(Item item, int quantite) {
        boolean ajouter = false;
        int actuel = mapItems.getOrDefault(item.getNom(), 0);

        if (actuel < item.getQuantiteMax()) {
            int nouveau = actuel + quantite;
            mapItems.put(item.getNom(), nouveau);
            ajouter = true;
        }
        return ajouter;
    }

    public boolean retirerItem(String nom) {
        return retirerItem(nom, 1);
    }

    public boolean retirerItem(String nom, int quantite) {
        boolean retirer = false;
        int actuel = mapItems.getOrDefault(nom, 0);
        if (actuel >= quantite) {
            int nouveau = actuel - quantite;
            mapItems.put(nom, nouveau);
            retirer = true;
        }
        return retirer;
    }


    public HashMap<String, Integer> getMapItems() {
        return mapItems;
    }

}
