package fr.iut.groupe.junglequest.modele.item.equipement.outil;

import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;
import fr.iut.groupe.junglequest.modele.item.equipement.condition.ConditionArbre;

public class Pioche extends Outil {
    public Pioche() {
        super("Pioche", 3, 2, new ConditionArbre());
    }

    @Override
    public int degatsBonus(String nomCible) {
        int degatsBonus = 0;
        if (nomCible.equals("Arbre")){
            degatsBonus = this.degats;
        }
        return degatsBonus;
    }

    @Override
    public boolean seConstruit(Equipement e) {
        Inventaire inventaire = Inventaire.getInstance();
        if (inventaire.retirerItem("Bois", 1) && inventaire.retirerItem("Pierre", 1) && inventaire.retirerItem("File", 1)){
            return true;
        }
        return false;
    }

}

