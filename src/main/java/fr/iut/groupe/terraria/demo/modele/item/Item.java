package fr.iut.groupe.terraria.demo.modele.item;

public abstract class Item {
    private String nom;
    public Item(String nom) {
        this.nom = nom;
    }

    public abstract int getQuantiteMax();
    public String getNom() {
        return nom;
    }
}
