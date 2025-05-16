package fr.iut.groupe.terraria.demo.modele.item.equipement;

import fr.iut.groupe.terraria.demo.modele.Inventaire;

import java.util.HashMap;
import java.util.Map;

public abstract class ArmeCraft extends Arme {

    public ArmeCraft(String nom, int degats, int portee) {
        super(nom, degats, portee);
    }

    public abstract boolean materiauxRequis(HashMap<String, Integer> mapRessouces);
    public abstract boolean construire(Inventaire inventaire, HashMap<String, Integer> mapRessouces);
}
