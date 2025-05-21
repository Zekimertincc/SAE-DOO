package fr.iut.groupe.terraria.demo.modele.item.equipement;

import fr.iut.groupe.terraria.demo.modele.Ciblable;
import fr.iut.groupe.terraria.demo.modele.monde.Maths;

public class Couteau extends Equipement {
    public Couteau() {
        super("Couteau", 10, "Arme", 100000, 1, 0, 0, 0);
    }

    // retourne les degats selon la situation
    @Override
    public int degatsContre(double x1, double y1, Ciblable cible){
        int degatsFinal = 0;
        if (Maths.distance(x1, y1, cible.getX(), cible.getY())<=1){
            if (cible.getNom().equals("Roche")){
                degatsFinal = this.degats;
            }
        }
        return degatsFinal;
    }

    @Override
    public void utiliser() {
        durabilite -= 0;
    }
}
