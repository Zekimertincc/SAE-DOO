package fr.iut.groupe.junglequest.modele.item.equipement.condition;

import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;
import java.util.ArrayList;

public class ConditionComposite implements ConditionBonus{
    private ArrayList<ConditionBonus> listConditionBonus;

    public ConditionComposite (){
        this.listConditionBonus = new ArrayList<>();
    }

    @Override
    public boolean verification(Equipement equipement) {
        for (ConditionBonus c : listConditionBonus){
            if (!verification(equipement)){
                return false;
            }
        }
        degatsBonus(equipement);
        return true;
    }

    @Override
    public int degatsBonus(Equipement equipement) {
        return equipement.getDegats();
    }
}
