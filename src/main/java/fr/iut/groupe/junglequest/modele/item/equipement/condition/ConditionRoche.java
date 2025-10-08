package fr.iut.groupe.junglequest.modele.item.equipement.condition;

import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;

public class ConditionRoche implements ConditionBonus{
    @Override
    public boolean verification(Equipement equipement) {
        if (equipement.getNom().equals("Roche")){
            return true;
        }
        return false;
    }

    @Override
    public int degatsBonus(Equipement equipement) {
        return equipement.getDegats();
    }
}
