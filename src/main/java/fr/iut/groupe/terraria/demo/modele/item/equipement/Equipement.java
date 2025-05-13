package fr.iut.groupe.terraria.demo.modele.item.equipement;
import fr.iut.groupe.terraria.demo.modele.item.Item;

public class Equipement extends Item {
    private int degats;

    public Equipement(String nom, int degats) {
        super(nom);
        this.degats = degats;
    }

    public int getDegats() {
        return this.degats;
    }
}
