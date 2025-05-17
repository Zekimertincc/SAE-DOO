package fr.iut.groupe.terraria.demo.modele.item.equipement.outil;

import fr.iut.groupe.terraria.demo.modele.Ciblable;
import fr.iut.groupe.terraria.demo.modele.item.equipement.Equipement;

public abstract class Outil extends Equipement {
    public Outil(String nom, int degats, int portee) {
        super(nom, degats, "outil",15, portee);
    }

    public abstract int degatsContre(double x1, double y2, Ciblable cible);

    public void utiliser() {
        if (!estCasse()) {
            durabilite--;
        }
    }
}