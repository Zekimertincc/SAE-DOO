package fr.iut.groupe.junglequest.modele.item.equipement.arme;

import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;
import fr.iut.groupe.junglequest.modele.item.equipement.condition.ConditionEnnemi;

public abstract class Arme extends Equipement {
    public Arme(String nom, int degats, int portee) {
        super(nom, degats, 20, portee, new ConditionEnnemi());
    }

    public abstract boolean seConstruit (Equipement e);

//    @Override
//    public int degatsBonus (String nomCible){
//        int degatsBonus = 0;
//        if (nomCible.equals("Ennemi")){
//            degatsBonus = this.degats;
//        }
//        return degatsBonus;
//    }

    public void utiliser() {
        if (!estCasse()) {
            durabilite -= 2;
        }
    }
}
