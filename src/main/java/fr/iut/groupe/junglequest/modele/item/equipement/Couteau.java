package fr.iut.groupe.junglequest.modele.item.equipement;

import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.item.equipement.condition.ConditionBonus;

public class Couteau extends Equipement {
    public Couteau(ConditionBonus conditionBonus) {
        super("Couteau", 10, 100000, 50, conditionBonus);
    }


    @Override
    public boolean seConstruit(Inventaire inventaire, Equipement e) {
        return false;
    }

//    @Override
//    public int degatsBonus(String nomCible) {
//        return 0;
//    }

    @Override
    public void utiliser() {
        durabilite -= 0;
    }
}
