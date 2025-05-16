package fr.iut.groupe.terraria.demo.modele.item.equipement.arme;

import fr.iut.groupe.terraria.demo.modele.Inventaire;
import fr.iut.groupe.terraria.demo.modele.item.equipement.ArmeCraft;

import java.util.HashMap;

public class Bombe extends Arme implements ArmeCraft {
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
        inventaire.ajouterEquipement(new Bombe());
        return true;
    }

}
