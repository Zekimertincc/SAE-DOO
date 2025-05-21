package fr.iut.groupe.terraria.demo.modele.item.equipement.outil;

import fr.iut.groupe.terraria.demo.modele.Ciblable;
import fr.iut.groupe.terraria.demo.modele.Inventaire;
import fr.iut.groupe.terraria.demo.modele.item.equipement.arme.Arc;
import fr.iut.groupe.terraria.demo.modele.monde.Maths;

import java.util.HashMap;

public class Hache extends Outil {
    public Hache() {
        super("Hache", 3, 2, 10, 5, 2);
    }

    // retourne les degats selon la situation
    @Override
    public int degatsContre(double x1, double y1, Ciblable cible){
        int degatsFinal = 0;
        if (Maths.distance(x1, y1, cible.getX(), cible.getY())<=2){
            degatsFinal = this.degats;
            if (cible.getNom().equals("Arbre")){
                degatsFinal *= 2;
            }
        }
        return degatsFinal;
    }
}
