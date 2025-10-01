package fr.iut.groupe.junglequest.modele.item.equipement.arme;

import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;
import fr.iut.groupe.junglequest.modele.item.equipement.Recette;

public abstract class Arme extends Equipement implements Recette {
    public Arme(String nom, int degats, int portee) {
        super(nom, degats, 20, portee);
    }

    @Override
    public int degatsBonus (String nomCible){
        int degatsBonus = 0;
        if (nomCible.equals("Ennemi")){
            degatsBonus = this.degats;
        }
        return degatsBonus;
    }

    public void utiliser() {
        if (!estCasse()) {
            durabilite -= 2;
        }
    }
}
