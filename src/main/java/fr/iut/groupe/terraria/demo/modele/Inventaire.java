package fr.iut.groupe.terraria.demo.modele;

import fr.iut.groupe.terraria.demo.modele.item.Item;
import fr.iut.groupe.terraria.demo.modele.item.nourriture.Nourriture;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventaire {
    private ArrayList<Item> itemsUniques; // les armes on a le droit d'avoir un exemplaire d'arme max
    private HashMap<String, Integer> ressources; // le bois par exemple avec sa quantité
    private final int LIMITE_PAR_RESSOURCE = 50;

    public Inventaire() {
        this.itemsUniques = new ArrayList<>();
        this.ressources = new HashMap<>();
    }
    public boolean ajouterItem(Item item) {
        for (Item i : itemsUniques) {
            if (i.getNom().equals(item.getNom())) {
                return false; // déjà présent
            }
        }
        itemsUniques.add(item);
        return true;
    }

    public boolean retirerItem(String nom) {
        for (Item i : itemsUniques) {
            if (i.getNom().equals(nom)) {
                itemsUniques.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean contientItem(String nom) {
        for (Item i : itemsUniques) {
            if (i.getNom().equals(nom)) {
                return true;
            }
        }
        return false;
    }

    public boolean utiliserItem(String nomItem, Joueur joueur) {
        for (int i = 0; i < itemsUniques.size(); i++) {
            Item item = itemsUniques.get(i);
            if (item.getNom().equals(nomItem)) {
                if (item instanceof Nourriture) {
                    ((Nourriture) item).utiliserSur(joueur);
                    retirerItem(nomItem);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean ajouterRessource(String nom, int quantite) {
        int actuel = ressources.getOrDefault(nom, 0);
        if (actuel + quantite > LIMITE_PAR_RESSOURCE) {
            System.out.println("Limite atteinte pour : " + nom);
            return false;
        }
        ressources.put(nom, actuel + quantite);
        return true;
    }

    public boolean retirerRessource(String nom, int quantite) {
        int actuel = ressources.getOrDefault(nom, 0);
        if (actuel < quantite) return false;

        ressources.put(nom, actuel - quantite);
        return true;
    }

    public boolean contientRessource(String nom, int quantite) {
        return ressources.getOrDefault(nom, 0) >= quantite;
    }

    public void afficher() {
        System.out.println("Objets uniques :");
        for (Item i : itemsUniques) {
            System.out.println(" - " + i.getNom());
        }

        System.out.println("Ressources :");
        for (String nom : ressources.keySet()) {
            System.out.println(" - " + nom + " × " + ressources.get(nom));
        }
    }

    public ArrayList<Item> getItemsUniques() {
        return itemsUniques;
    }

    public HashMap<String, Integer> getRessources() {
        return ressources;
    }
}
