package fr.iut.groupe.terraria.demo.modele.item.equipement;

import fr.iut.groupe.terraria.demo.modele.Inventaire;

import java.util.HashMap;
import java.util.Map;

public class Bombe extends ArmeCraft {
    public Bombe() {
        super("Bombe", 10, 5);
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
        inventaire.retirerRessource("Bois", 20);
        inventaire.retirerRessource("Pierre", 15);
        inventaire.retirerRessource("Fil", 5);
        inventaire.ajouterItem(new Bombe());
        return true;
    }

}
