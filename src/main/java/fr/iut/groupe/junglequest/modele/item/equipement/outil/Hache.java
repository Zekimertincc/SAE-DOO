package fr.iut.groupe.junglequest.modele.item.equipement.outil;

public class Hache extends Outil {
    public Hache() {
        super("Hache", 3, 2, 10, 5, 2);
    }

    @Override
    public int degatsBonus(String nomCible) {
        int degatsBonus = 0;
        if (nomCible.equals("Roche")){
            degatsBonus = this.degats;
        }
        return degatsBonus;
    }

}
