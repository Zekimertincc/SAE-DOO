package fr.iut.groupe.terraria.demo.modele.item.equipement;

public class Couteau extends Equipement {
    public Couteau() {
        super("Couteau", 10, "Arme", 100000, 1, 0, 0, 0, "Rien");
    }

    @Override
    public void utiliser() {
        durabilite -= 0;
    }
}
