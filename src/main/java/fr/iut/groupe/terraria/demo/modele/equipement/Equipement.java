package fr.iut.groupe.terraria.demo.modele.equipement;
import fr.iut.groupe.terraria.demo.modele.*;

public class Equipement extends Item{
    private int degats;

    public Equipement(int degats) {
        super("equipement");
        this.degats = degats;
    }
    public int getDegats() {
        return this.degats;
    }
}
