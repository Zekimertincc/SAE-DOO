package fr.iut.groupe.terraria.demo.modele;

import fr.iut.groupe.terraria.demo.modele.item.Item;
import java.util.HashMap;

public class Inventaire {
    private HashMap<String, Integer> mapItems; // ex: "Bois" -> 3
    private boolean modifie;

    public Inventaire() {
        this.mapItems = new HashMap<>();
        this.modifie = false;
    }

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

        if (actuel + quantite <= item.getQuantiteMax()) {
            mapItems.put(item.getNom(), actuel + quantite);
            ajouter = true;
            modifie = true;
        }
        return ajouter;
    }

    public boolean possede(String nomItem) {
        return mapItems.containsKey(nomItem) && mapItems.get(nomItem) > 0;
    }

    public boolean utiliser(String nomItem) {
        if (possede(nomItem)) {
            int quantite = mapItems.get(nomItem);
            if (quantite == 1) {
                mapItems.remove(nomItem);
            } else {
                mapItems.put(nomItem, quantite - 1);
            }
            modifie = true;
            return true;
        }
        return false;
    }

    public int getQuantite(String nomItem) {
        return mapItems.getOrDefault(nomItem, 0);
    }

    public boolean isModifie() {
        return modifie;
    }

    public void setModifie(boolean modifie) {
        this.modifie = modifie;
    }
}
