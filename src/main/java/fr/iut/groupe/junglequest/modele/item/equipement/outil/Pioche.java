package fr.iut.groupe.junglequest.modele.item.equipement.outil;

public class Pioche extends Outil {
    public Pioche() {
        super("Pioche", 3, 2, 10, 5, 2);
    }

    @Override
    public int degatsBonus(String nomCible) {
        int degatsBonus = 0;
        if (nomCible.equals("Arbre")){
            degatsBonus = this.degats;
        }
        return degatsBonus;
    }
}

