package fr.iut.groupe.junglequest.modele.item.equipement.arme;

import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;
import fr.iut.groupe.junglequest.modele.item.equipement.condition.ConditionBonus;

public class Arc extends Arme{
    public Arc() {
        super("Arc", 10, 20);
    }

    @Override
    public boolean seConstruit(Inventaire inventaire, Equipement e) {
        if (inventaire.retirerItem("Bois", 1) && inventaire.retirerItem("Pierre", 1) && inventaire.retirerItem("File", 1)){
            return true;
        }
        return false;
    }
}
