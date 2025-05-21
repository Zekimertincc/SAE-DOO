package fr.iut.groupe.terraria.demo.modele;

import fr.iut.groupe.terraria.demo.modele.item.Item;
import java.util.HashMap;

public class Inventaire {
    private HashMap<String, Integer> mapItems; // le bois par exemple avec sa quantité
    private boolean modifie;

    public Inventaire() {
        this.mapItems = new HashMap<>();
        this.modifie = false;
    }

    // ajouter un item dans inventaire
    public boolean ajouterItem(Item item) {
        boolean ajouter = false;
        int actuel = mapItems.getOrDefault(item.getNom(), 0);

        if (actuel < item.getQuantiteMax()) {
            mapItems.put(item.getNom(), actuel + 1);
            ajouter = true;
            modifie = true;
        }
        return ajouter;
    }
    public boolean ajouterItem(Item item, int quantite) {
        boolean ajouter = false;
        int actuel = mapItems.getOrDefault(item.getNom(), 0);

        if (actuel < item.getQuantiteMax()) {
            mapItems.put(item.getNom(), actuel + quantite);
            ajouter = true;
            modifie = true;
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
            modifie = true;
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

    // voir les changements d'inventaire
    public boolean mettreAJourInventaire() {
        boolean resultat = modifie;
        modifie = false;
        return resultat;
    }

    public void afficherMap () {
        for (HashMap.Entry<String, Integer> item : mapItems.entrySet()) {
            System.out.println("Item : " + item.getKey() + " | Quantité : " + item.getValue());
        }
    }
    public HashMap<String, Integer> getMapItems() {
        return mapItems;
    }
}