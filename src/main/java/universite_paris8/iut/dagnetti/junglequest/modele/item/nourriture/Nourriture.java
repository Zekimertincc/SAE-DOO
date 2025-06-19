package universite_paris8.iut.dagnetti.junglequest.modele.item.nourriture;

import universite_paris8.iut.dagnetti.junglequest.modele.item.Item;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Joueur;


public abstract class Nourriture extends Item {
    protected int vie;
    private int quantiteMax;

    public Nourriture(String nom, int vie) {
        super(nom);
        this.vie = vie;
        this.quantiteMax = 10;
    }
    @Override
    public int getQuantiteMax() {
        return this.quantiteMax;
    }
    // modifie la vie du joueur
    public void utiliserSur(Joueur joueur) {
        if (joueur.getPointsDeVie()<joueur.getVieMax()){
           // joueur.gagnerVie(vie);
        }
        appliquerEffetSecondaire(joueur);
    }

    public abstract void appliquerEffetSecondaire(Joueur joueur);

    public int getVie() {
        return vie;
    }
}

