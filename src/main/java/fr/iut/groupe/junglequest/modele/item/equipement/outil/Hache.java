package fr.iut.groupe.junglequest.modele.item.equipement.outil;

import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;
import fr.iut.groupe.junglequest.modele.item.equipement.condition.ConditionArbre;

public class Hache extends Outil {
    public Hache() {
        super("Hache", 3, 2, new ConditionArbre());
    }

    @Override
    public int degatsBonus(String nomCible) {
        int degatsBonus = 0;
        if (nomCible.equals("Roche")){
            degatsBonus = this.degats;
        }
        return degatsBonus;
    }

    @Override
    public boolean seConstruit(Equipement e) {
        Inventaire inventaire = Inventaire.getInstance();
        if (inventaire.retirerItem("Bois", 6) && inventaire.retirerItem("Pierre", 4) && inventaire.retirerItem("File", 5)){
            return true;
        }
        return false;
    }


}
