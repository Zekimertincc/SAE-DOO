package fr.iut.groupe.terraria.demo.modele.item.equipement.outil;

import fr.iut.groupe.terraria.demo.modele.item.equipement.Equipement;

public class Outil extends Equipement {
    public Outil(String nom, int degats, int portee) {
        super(nom, degats, "outil",20, portee);
    }
}