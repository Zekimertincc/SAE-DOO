package fr.iut.groupe.terraria.demo.modele.item.equipement.arme;

import fr.iut.groupe.terraria.demo.modele.Ciblable;
import fr.iut.groupe.terraria.demo.modele.item.equipement.Equipement;

public abstract class Arme extends Equipement {
    public Arme(String nom, int degats, int portee, int quantiteBois, int quantitePierre, int quantiteFile) {
        super(nom, degats, "Arme", 20, portee, quantiteBois, quantitePierre, quantiteFile);
    }

    // retourne les degats selon la situation
    public abstract int degatsContre(double x1, double y2, Ciblable cible);

    public void utiliser() {
        if (!estCasse()) {
            durabilite -= 2;
        }
    }
}
