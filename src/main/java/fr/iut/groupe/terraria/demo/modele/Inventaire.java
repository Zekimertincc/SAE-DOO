package fr.iut.groupe.terraria.demo.modele;

import fr.iut.groupe.terraria.demo.modele.item.Item;

import java.util.ArrayList;
import java.util.List;

public class Inventaire {
    private ArrayList<Item> inventaire;

    public Inventaire(ArrayList<Item> inventaire) {
        this.inventaire = new ArrayList<>();
    }

    public void ajouterItem(Item item) {
        inventaire.add(item);
    }

    // regarde si le personnage a l'item retourne true si oui sinon false.
    public boolean possede (String nomItem) {
        for (Item i : inventaire) {
            if (i.getNom().equals(nomItem)){
                return true;
            }
        }
        return false;
    }
    // envoie un item en parametre est s'il est dans l'inventaire alors il s'enleve de l'inventaire (retourne true) sinon false
    public boolean utiliser(String nomItem){
        for (Item i : inventaire) {
            if (i.getNom().equals(nomItem)){
                inventaire.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Item> getInventaire() {
        return inventaire;
    }
}
