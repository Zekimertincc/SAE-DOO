package fr.iut.groupe.terraria.demo.modele;

import fr.iut.groupe.terraria.demo.modele.item.Item;
import fr.iut.groupe.terraria.demo.modele.item.nourriture.Nourriture;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;

import java.util.ArrayList;

public class Inventaire {
    private ArrayList<Item> listItems; // list de item, on a le droit d'avoir qu'un exemplaire par item.

    public Inventaire() {
        listItems = new ArrayList<>();
    }

    // ajoute un item s'il n'est pas déjà présent
    public boolean ajouter(Item item) {
        for (Item i : listItems) {
            if (i.getNom().equals(item.getNom())) {
                return false;
            }
        }
        listItems.add(item);
        return true;
    }
    public boolean utiliserItem(String nomItem, Joueur joueur) {
        boolean utiliser = false;
        for (int i = 0; i < listItems.size() && !utiliser; i++) {
            Item item = listItems.get(i);
            if (item.getNom().equals(nomItem)) {
                if (item instanceof Nourriture) {
                    Nourriture nourriture = (Nourriture) item;
                    nourriture.utiliserSur(joueur);
                    retirer(nomItem);
                    utiliser = true;
                }
            }
        }
        return utiliser;
    }

    // retire un item par nom
    public boolean retirer(String nom) {
        for (Item i : listItems) {
            if (i.getNom().equals(nom)) {
                listItems.remove(i);
                return true;
            }
        }
        return false;
    }

    // verifie si un item est présent
    public boolean contient(String nom) {
        for (Item i : listItems) {
            if (i.getNom().equals(nom)) {
                return true;
            }
        }
        return false;
    }

    // affiche le contenu de l'inventaire
    public void afficher() {
        System.out.println("Inventaire :");
        for (Item i : listItems) {
            System.out.println(" ,"+i.getNom());
        }
    }
}
