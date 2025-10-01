package fr.iut.groupe.junglequest.modele.item.equipement.arme;

import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;

public class Bombe extends Arme {
    public Bombe() {
        super("Bombe", 10, 5);
    }

    @Override
    public boolean verificationConstructiion(Inventaire inventaire, Equipement p) {
        if (inventaire.retirerItem("Bois", 3) && inventaire.retirerItem("Pierre", 5)
                && inventaire.retirerItem("File", 16)){
            return true;
        }
        return false;
    }
}
