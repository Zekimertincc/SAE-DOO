package fr.iut.groupe.terraria.demo.modele;

import fr.iut.groupe.terraria.demo.modele.item.Item;
import fr.iut.groupe.terraria.demo.modele.item.nourriture.Nourriture;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventaire {
    private ArrayList<Item> listArmes; // les armes on a le droit d'avoir un exemplaire d'arme max
    private HashMap<String, Integer> mapRessources; // le bois par exemple avec sa quantité
    private final int LIMITE_PAR_RESSOURCE = 50;

    public Inventaire() {
        this.listArmes = new ArrayList<>();
        this.mapRessources = new HashMap<>();
    }
    public boolean ajouterItem(Item item) {
        for (Item i : listArmes) {
            if (i.getNom().equals(item.getNom())) {
                return false; // déjà présent
            }
        }
        listArmes.add(item);
        return true;
    }

    public boolean retirerItem(String nom) {
        for (Item i : listArmes) {
            if (i.getNom().equals(nom)) {
                listArmes.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean contientItem(String nom) {
        for (Item i : listArmes) {
            if (i.getNom().equals(nom)) {
                return true;
            }
        }
        return false;
    }

    public boolean utiliserItem(String nomItem, Joueur joueur) {
        for (int i = 0; i < listArmes.size(); i++) {
            Item item = listArmes.get(i);
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
        int actuel = mapRessources.getOrDefault(nom, 0);
        if (actuel + quantite > LIMITE_PAR_RESSOURCE) {
            System.out.println("Limite atteinte pour : " + nom);
            return false;
        }
        mapRessources.put(nom, actuel + quantite);
        return true;
    }
    public boolean retirerRessource(String nom, int quantite) {
        int actuel = mapRessources.getOrDefault(nom, 0);
        if (actuel < quantite) return false;

        mapRessources.put(nom, actuel - quantite);
        return true;
    }

    public boolean contientRessource(String nom, int quantite) {
        return mapRessources.getOrDefault(nom, 0) >= quantite;
    }

    public void afficher() {
        System.out.println("Objets uniques :");
        for (Item i : listArmes) {
            System.out.println(" - " + i.getNom());
        }

        System.out.println("Ressources :");
        for (String nom : mapRessources.keySet()) {
            System.out.println(" - " + nom + " × " + mapRessources.get(nom));
        }
    }

    public ArrayList<Item> getItemsUniques() {
        return listArmes;
    }

    public HashMap<String, Integer> getMapRessources() {
        return mapRessources;
    }
}
