package fr.iut.groupe.junglequest.modele.item.equipement.outil;

import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;
import fr.iut.groupe.junglequest.modele.item.equipement.Recette;

public abstract class Outil extends Equipement implements Recette {
    public Outil(String nom, int degats, int portee) {
        super(nom, degats,15, portee);
    }

    public abstract int degatsBonus (String nomCible);

    public void utiliser() {
        if (!estCasse()) {
            durabilite--;
        }
    }
}
