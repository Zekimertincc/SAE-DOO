package fr.iut.groupe.junglequest.modele.item.equipement;

import fr.iut.groupe.junglequest.modele.item.Inventaire;

public class Couteau extends Equipement {
    public Couteau() {
        super("Couteau", 10, 100000, 50);
    }


    @Override
    public boolean seConstruit(Inventaire inventaire, Equipement e) {
        return false;
    }

    @Override
    public int degatsBonus(String nomCible) {
        return 0;
    }

    @Override
    public void utiliser() {
        durabilite -= 0;
    }
}
