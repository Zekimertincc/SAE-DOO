package fr.iut.groupe.junglequest.modele.item.equipement.condition;

import fr.iut.groupe.junglequest.modele.Ciblable;
import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;

public interface ConditionBonus {
    boolean verification (Ciblable cible);
    int degatsBonus (Equipement equipement);
}
