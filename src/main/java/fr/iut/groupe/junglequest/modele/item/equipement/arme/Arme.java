package fr.iut.groupe.junglequest.modele.item.equipement.arme;

import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;

public abstract class Arme extends Equipement {
    public Arme(String nom, int degats, int portee, int quantiteBois, int quantitePierre, int quantiteFile) {
        super(nom, degats, 20, portee, quantiteBois, quantitePierre, quantiteFile);
    }

    @Override
    public int degatsBonus (String nomCible){
        int degatsBonus = 0;
        if (nomCible.equals("Ennemi")){
            degatsBonus = this.degats;
        }
        return degatsBonus;
    }

    public void utiliser() {
        if (!estCasse()) {
            durabilite -= 2;
        }
    }
}
