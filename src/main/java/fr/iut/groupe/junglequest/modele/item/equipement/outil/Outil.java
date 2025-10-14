package fr.iut.groupe.junglequest.modele.item.equipement.outil;
import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;

public abstract class Outil extends Equipement {
    public Outil(String nom, int degats, int portee) {
        super(nom, degats,15, portee);
    }
    public abstract int degatsBonus (String nomCible);
    public abstract boolean seConstruit (Inventaire inventaire, Equipement e);

    public void utiliser() {
        if (!estCasse()) {
            durabilite--;
        }
    }
}
