package fr.iut.groupe.junglequest.modele.item.equipement.arme;

import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;

public class Arc extends Arme{
    public Arc() {
        super("Arc", 10, 20);
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
