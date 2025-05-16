package fr.iut.groupe.terraria.demo.modele.item.equipement.outil;

import fr.iut.groupe.terraria.demo.modele.Inventaire;
import fr.iut.groupe.terraria.demo.modele.item.equipement.arme.Arc;

import java.util.HashMap;

public class Hache extends Outil {
    public Hache() {
        super("Hache", 3, 2);
    }

    public boolean materiauxRequis(HashMap<String, Integer> mapRessouces) {
        return mapRessouces.getOrDefault("Bois", 0) >= 10 &&
                mapRessouces.getOrDefault("Pierre", 0) >= 5 &&
                mapRessouces.getOrDefault("Fil", 0) >= 2;
    }

    public boolean construire(Inventaire inventaire, HashMap<String, Integer> mapRessouces) {
        if (!materiauxRequis(mapRessouces)) {
            return false;
        }
        inventaire.retirerRessource("Bois", 10);
        inventaire.retirerRessource("Pierre", 5);
        inventaire.retirerRessource("Fil", 2);
        inventaire.ajouterEquipement(new Hache());
        return true;
    }
}
