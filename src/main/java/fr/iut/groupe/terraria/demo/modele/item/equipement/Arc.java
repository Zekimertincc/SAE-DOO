package fr.iut.groupe.terraria.demo.modele.item.equipement;

import fr.iut.groupe.terraria.demo.modele.Inventaire;

import java.util.HashMap;

public class Arc extends ArmeCraft {
    public Arc() {
        super("Arc", 10, 20);
    }
    public boolean materiauxRequis(HashMap<String, Integer> mapRessouces) {
        return mapRessouces.getOrDefault("Bois", 0) >= 30 &&
                mapRessouces.getOrDefault("Pierre", 0) >= 5 &&
                mapRessouces.getOrDefault("Fil", 0) >= 10;
    }

    public boolean construire(Inventaire inventaire, HashMap<String, Integer> mapRessouces) {
        if (!materiauxRequis(mapRessouces)) {
            return false;
        }
        inventaire.retirerRessource("Bois", 30);
        inventaire.retirerRessource("Pierre", 5);
        inventaire.retirerRessource("Fil", 10);
        inventaire.ajouterItem(new Arc());
        return true;
    }
}
