package fr.iut.groupe.junglequest.modele.item.equipement.condition;

import fr.iut.groupe.junglequest.modele.Ciblable;
import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;

public class ConditionArbre implements ConditionBonus{
    @Override
    public boolean verification(Ciblable cible) {
        if (cible.getNom().equals("Arbre")){
            return true;
        }
        return false;
    }

    @Override
    public int degatsBonus(Equipement equipement) {
        return equipement.getDegats() + 10;
    }
}
