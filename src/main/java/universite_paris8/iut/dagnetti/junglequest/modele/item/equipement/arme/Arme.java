package universite_paris8.iut.dagnetti.junglequest.modele.item.equipement.arme;

import universite_paris8.iut.dagnetti.junglequest.modele.item.equipement.Equipement;

public abstract class Arme extends Equipement {
    public Arme(String nom, int degats, int portee, int quantiteBois, int quantitePierre, int quantiteFile) {
        super(nom, degats, "Arme", 20, portee, quantiteBois, quantitePierre, quantiteFile, "Ennemi");
    }

    public void utiliser() {
        if (!estCasse()) {
            durabilite -= 2;
        }
    }
}
