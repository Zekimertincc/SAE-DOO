package fr.iut.groupe.terraria.demo.modele.item.equipement.outil;

import fr.iut.groupe.terraria.demo.modele.Ciblable;
import fr.iut.groupe.terraria.demo.modele.Inventaire;
import fr.iut.groupe.terraria.demo.modele.item.equipement.ArmeCraft;
import fr.iut.groupe.terraria.demo.modele.item.equipement.arme.Arc;
import fr.iut.groupe.terraria.demo.modele.monde.Maths;

import java.util.HashMap;

public class Pioche extends Outil implements ArmeCraft {
    public Pioche() {
        super("Pioche", 3, 2);
    }

    // retourne les degats selon la situation
    @Override
    public int degatsContre(double x1, double y1, Ciblable cible){
        int degatsFinal = 0;
        if (Maths.distance(x1, y1, cible.getX(), cible.getY())<=2){
            degatsFinal = this.degats;
            if (cible.getNom().equals("Roche")){
                degatsFinal *= 2;
            }
        }
        return degatsFinal;
    }

    @Override
    public boolean materiauxRequis(HashMap<String, Integer> mapRessouces) {
        return mapRessouces.getOrDefault("Bois", 0) >= 10 &&
                mapRessouces.getOrDefault("Pierre", 0) >= 5 &&
                mapRessouces.getOrDefault("Fil", 0) >= 2;
    }

    @Override
    public boolean construire(Inventaire inventaire, HashMap<String, Integer> mapRessouces) {
        boolean contruire = false;
        if (!materiauxRequis(mapRessouces)) {
            if (inventaire.ajouterItem(new Pioche())){
                inventaire.retirerItem("Bois", 10);
                inventaire.retirerItem("Pierre", 5);
                inventaire.retirerItem("File", 2);
                contruire = true;
            }
        }
        return contruire;
    }

}

