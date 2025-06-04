package fr.iut.groupe.terraria.demo.modele;

import fr.iut.groupe.terraria.demo.modele.item.Item;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.HashMap;

public class Inventaire {
    private HashMap<String, Integer> mapItems;
    private HashMap<String, IntegerProperty> mapProperties;
    private boolean modifie;

    public Inventaire() {
        this.mapItems = new HashMap<>();
        this.mapProperties = new HashMap<>();
        this.modifie = false;
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
            getQuantiteProperty(item.getNom()).set(nouveau);
            ajouter = true;
            modifie = true;
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
            getQuantiteProperty(nom).set(nouveau);
            retirer = true;
            modifie = true;
        }
        return retirer;
    }

    public boolean mettreAJourInventaire() {
        boolean resultat = modifie;
        this.modifie = false;
        return resultat;
    }

    public void afficherMap () {
        for (HashMap.Entry<String, Integer> item : mapItems.entrySet()) {
            System.out.println("item : " + item.getKey() + " ,quantité : " + item.getValue());
        }
    }

    public HashMap<String, Integer> getMapItems() {
        return mapItems;
    }

    public IntegerProperty getQuantiteProperty(String nom) {
        mapProperties.putIfAbsent(nom, new SimpleIntegerProperty(mapItems.getOrDefault(nom, 0)));
        return mapProperties.get(nom);
    }

    public HashMap<String, IntegerProperty> getMapProperties() {
        return mapProperties;
    }
}
