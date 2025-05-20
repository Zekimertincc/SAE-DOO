package fr.iut.groupe.terraria.demo.modele.item.equipement.arme;

import fr.iut.groupe.terraria.demo.modele.Ciblable;
import fr.iut.groupe.terraria.demo.modele.Inventaire;
import fr.iut.groupe.terraria.demo.modele.item.equipement.ArmeCraft;
import fr.iut.groupe.terraria.demo.modele.monde.Maths;

import java.util.HashMap;

public class Arc extends Arme implements ArmeCraft {
    public Arc() {
        super("Arc", 10, 20);
    }

    // retourne les degats selon la situation
    @Override
    public int degatsContre(double x1, double y1, Ciblable cible){
        int degatsFinal = 0;
        if (Maths.distance(x1, y1, cible.getX(), cible.getY())<=10){
            degatsFinal = this.degats;
            if (cible.getNom().equals("Ennemi")){
                degatsFinal += 3;
            }
        }
        return degatsFinal;
    }

    @Override
    public boolean materiauxRequis(HashMap<String, Integer> mapRessouces) {
        return mapRessouces.getOrDefault("Bois", 0) >= 30 &&
                mapRessouces.getOrDefault("Pierre", 0) >= 5 &&
                mapRessouces.getOrDefault("Fil", 0) >= 10;
    }

    @Override
    public boolean construire(Inventaire inventaire, HashMap<String, Integer> mapRessouces) {
        boolean contruire = false;
        if (!materiauxRequis(mapRessouces)) {
            if (inventaire.ajouterItem(new Arc())){
                inventaire.retirerItem("Bois", 30);
                inventaire.retirerItem("Pierre", 5);
                inventaire.retirerItem("File", 10);
                contruire = true;
            }
        }
        return contruire;
    }
}
