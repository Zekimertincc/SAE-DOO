package fr.iut.groupe.terraria.demo.modele.item;

import java.util.HashMap;

public class Inventaire {

    private final HashMap<String, Integer> mapItems;

    public Inventaire() {
        this.mapItems = new HashMap<>();
    }

    // ----------------------------------------------------------------------------------------

    // ajouter un item dans inventaire
    public boolean ajouterItem(Item item) {
        boolean ajouter = false;
        int actuel = mapItems.getOrDefault(item.getNom(), 0);

        if (actuel < item.getQuantiteMax()) {
            mapItems.put(item.getNom(), actuel + 1);
            ajouter = true;
        }
        return ajouter;
    }
    public boolean ajouterItem(Item item, int quantite) {
        boolean ajouter = false;
        int actuel = mapItems.getOrDefault(item.getNom(), 0);

        if (actuel < item.getQuantiteMax()) {
            mapItems.put(item.getNom(), actuel + quantite);
            ajouter = true;
        }
        return ajouter;
    }

    // retirer un item dans inventaire
    public boolean retirerItem(String nom) {
        boolean retirer = false;
        int actuel = mapItems.getOrDefault(nom, 0);
        if (actuel > 0) {
            mapItems.put( nom, actuel - 1);
            retirer = true;
        }
        return retirer;
    }

    // retire une quantité d'items (choisi dans les parametres) dans l'inventaire
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
    // ----------------------------------------------------------------------------------------

}
