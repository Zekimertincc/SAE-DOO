package fr.iut.groupe.terraria.demo.modele;

import java.util.ArrayList;

public class Inventaire {
    private String nom;
    private String type;
    private ArrayList<Item> inventaire;

    public Inventaire(String nom, String type, ArrayList<Item> inventaire) {
        this.nom = nom;
        this.type = type;
        this.inventaire = new ArrayList<>();
    }

    public void ajouterItem(Item item) {
        inventaire.add(item);
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public ArrayList<Item> getInventaire() {
        return inventaire;
    }
}
