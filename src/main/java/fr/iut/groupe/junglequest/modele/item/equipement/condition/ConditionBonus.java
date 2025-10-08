package fr.iut.groupe.junglequest.modele.item.equipement.condition;

import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;

public interface ConditionBonus {
    boolean verification (Equipement equipement);
    int degatsBonus (Equipement equipement);
}
