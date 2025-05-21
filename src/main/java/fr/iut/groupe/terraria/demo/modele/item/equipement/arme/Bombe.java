package fr.iut.groupe.terraria.demo.modele.item.equipement.arme;

import fr.iut.groupe.terraria.demo.modele.Ciblable;
import fr.iut.groupe.terraria.demo.modele.Inventaire;
import fr.iut.groupe.terraria.demo.modele.monde.Maths;

import java.util.HashMap;

public class Bombe extends Arme {
    public Bombe() {
        super("Bombe", 10, 5, 30, 5, 10);
    }

    // retourne les degats selon la situation
    @Override
    public int degatsContre(double x1, double y1, Ciblable cible){
        int degatsFinal = 0;
        if (Maths.distance(x1, y1, cible.getX(), cible.getY())<=5){
            degatsFinal = this.degats;
            if (cible.getNom().equals("Ennemi")){
                degatsFinal += 5;
            }
        }
        return degatsFinal;
    }
}
