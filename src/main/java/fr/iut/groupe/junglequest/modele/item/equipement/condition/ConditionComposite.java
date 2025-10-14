package fr.iut.groupe.junglequest.modele.item.equipement.condition;

import fr.iut.groupe.junglequest.modele.Ciblable;
import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;

import java.util.ArrayList;

public class ConditionComposite implements ConditionBonus {
    private ArrayList<ConditionBonus> listConditionBonus;

    public ConditionComposite(ArrayList<ConditionBonus> listConditionBonus) {
        this.listConditionBonus = listConditionBonus;
    }

    @Override
    public boolean verification(Ciblable cible) {
        for (ConditionBonus c : listConditionBonus){
            if (!c.verification(cible)){
                return false;
            }
        }
        return true;
    }

    @Override
    public int degatsBonus(Equipement equipement) {
        return equipement.getDegats();
    }
}
