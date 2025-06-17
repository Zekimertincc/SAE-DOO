package universite_paris8.iut.dagnetti.junglequest.modele;

import universite_paris8.iut.dagnetti.junglequest.modele.item.Item;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.HashMap;

public class Inventaire {
    private HashMap<String, Integer> mapItems;
    private HashMap<String, IntegerProperty> mapProperties;
    private Item itemActif;

    public Inventaire() {
        this.mapItems = new HashMap<>();
        this.mapProperties = new HashMap<>();
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
        }
        return retirer;
    }

    public void afficherMap() {
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

    public void setItemActif(Item item) {
        this.itemActif = item;
    }

    public Item getItemActif() {
        return itemActif;
    }
}
