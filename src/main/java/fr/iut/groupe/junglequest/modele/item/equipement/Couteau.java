package fr.iut.groupe.junglequest.modele.item.equipement;

public class Couteau extends Equipement {
    public Couteau() {
        super("Couteau", 10, 100000, 50);
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
