package fr.iut.groupe.junglequest.modele.item.equipement.arme;

import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;
import fr.iut.groupe.junglequest.modele.item.equipement.condition.ConditionBonus;

public class Bombe extends Arme {
    public Bombe() {
        super("Bombe", 10, 5);
    }


    @Override
    public boolean seConstruit(Equipement e) {
        Inventaire inventaire = Inventaire.getInstance();
        if (inventaire.retirerItem("Bois", 2) && inventaire.retirerItem("Pierre", 10) && inventaire.retirerItem("File", 3)){
            return true;
        }
        return false;
    }
}
