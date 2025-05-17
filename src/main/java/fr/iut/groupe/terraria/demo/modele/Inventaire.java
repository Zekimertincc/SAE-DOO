package fr.iut.groupe.terraria.demo.modele;

import fr.iut.groupe.terraria.demo.modele.item.Item;
import fr.iut.groupe.terraria.demo.modele.item.equipement.Equipement;

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
    // permet d'ajouter une arme
    public boolean ajouterEquipement(Equipement equipement) {
        boolean existeDeja = false;
        for (Item i : listArmes) {
            if (i.getNom().equals(equipement.getNom())) {
                existeDeja = true;
            }
        }
        if (!existeDeja) {
            listArmes.add(equipement);
        }
        return !existeDeja;
    }
    public boolean ajouterItem(Item item) {
        boolean ajoute = false;

        // On suppose que c'est une ressource (nom générique + quantité)
        int quantiteActuelle = mapRessources.getOrDefault(item.getNom(), 0);

        if (quantiteActuelle + 1 <= LIMITE_PAR_RESSOURCE) {
            mapRessources.put(item.getNom(), quantiteActuelle + 1);
            ajoute = true;
        }
        return ajoute;
    }

    // permet de regarder si un item à partir de son nom est dans la liste
    public boolean contientItem(String nom) {
        for (Item i : listArmes) {
            if (i.getNom().equals(nom)) {
                return true;
            }
        }
        return false;
    }
    // recupere la ressource actuel et regarde si ca depasse
    public boolean ajouterRessource(String nom, int quantite) {
        int actuel = mapRessources.getOrDefault(nom, 0);
        if (actuel + quantite > LIMITE_PAR_RESSOURCE) {
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
