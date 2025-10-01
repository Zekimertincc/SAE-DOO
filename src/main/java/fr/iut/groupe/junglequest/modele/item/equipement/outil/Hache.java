package fr.iut.groupe.junglequest.modele.item.equipement.outil;

import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;

public class Hache extends Outil {
    public Hache() {
        super("Hache", 3, 2);
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
    public boolean verificationConstructiion(Inventaire inventaire, Equipement p) {
        if (inventaire.retirerItem("Bois", 2) && inventaire.retirerItem("Pierre", 2)
                && inventaire.retirerItem("File", 2)){
            return true;
        }
        return false;
    }
}
