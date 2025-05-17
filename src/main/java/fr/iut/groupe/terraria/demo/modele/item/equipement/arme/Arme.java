package fr.iut.groupe.terraria.demo.modele.item.equipement.arme;

import fr.iut.groupe.terraria.demo.modele.Ciblable;
import fr.iut.groupe.terraria.demo.modele.item.equipement.Equipement;

public abstract class Arme extends Equipement {
    public Arme(String nom, int degats, int portee) {
        super(nom, degats, "arme", 20, portee);
    }

    // retourne les degats selon la situation
    public abstract int degatsContre(double x1, double y2, Ciblable cible);

    public void utiliser() {
        if (!estCasse()) {
            durabilite -= 2;
        }
    }
}
