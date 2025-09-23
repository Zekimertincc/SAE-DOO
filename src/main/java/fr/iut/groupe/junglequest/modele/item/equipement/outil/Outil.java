package fr.iut.groupe.junglequest.modele.item.equipement.outil;

import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;

public abstract class Outil extends Equipement {
    public Outil(String nom, int degats, int portee, int quantiteBois, int quantitePierre, int quantiteFile, String ciblePreferable) {
        super(nom, degats, "Outil",15, portee, quantiteBois, quantitePierre, quantiteFile, ciblePreferable);
    }

    public abstract int degatsBonus (String nomCible);

    public void utiliser() {
        if (!estCasse()) {
            durabilite--;
        }
    }
}
