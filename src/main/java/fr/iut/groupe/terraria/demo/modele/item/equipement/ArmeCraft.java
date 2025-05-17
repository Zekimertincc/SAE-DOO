package fr.iut.groupe.terraria.demo.modele.item.equipement;

import fr.iut.groupe.terraria.demo.modele.Inventaire;

import java.util.HashMap;

public interface ArmeCraft {

    // regarde si les ressources sont suffisante pour construire l'arme
    boolean materiauxRequis(HashMap<String, Integer> mapRessouces);

    // contruit l'arme et il le met directement dans l'inventaire du joueur
    boolean construire(Inventaire inventaire, HashMap<String, Integer> mapRessouces);
}
