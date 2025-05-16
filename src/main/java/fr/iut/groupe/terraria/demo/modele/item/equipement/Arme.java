package fr.iut.groupe.terraria.demo.modele.item.equipement;

public abstract class Arme extends Equipement {
    public Arme(String nom, int degats, int portee) {
        super(nom, degats, "arme", 10, portee);
    }
}
