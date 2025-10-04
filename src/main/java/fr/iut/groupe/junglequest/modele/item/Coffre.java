package fr.iut.groupe.junglequest.modele.item;

import fr.iut.groupe.junglequest.modele.monde.Maths;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;

public class Coffre extends Recompense {
    private double x, y;
    private boolean ouvert; // false si le coffre n'est pas ouvert

    public Coffre(Inventaire inventaire, double x, double y) {
        super(inventaire);
        this.x = x;
        this.y = y;
        this.ouvert = false;
    }

    /**
     * ouvre un coffre si c'est de la nourriture ca l'utilise directement sur le joueur
     * Si c'est de du farm (bois, pierre ou file) alors 10 dans l'inventaire, sinon on met une arme
     * @param joueur pour avoir la distance entre le joueur et le coffre
     */
    public void interactionAvecCoffre(Joueur joueur) {
        double distance = Maths.distance(joueur.getX(), joueur.getY(), this.getX(), this.getY());
        if (distance < 10) {
            Item randomItem = randomItem();
                if (!ouvert && randomItem != null){
                    randomItem.actionRecompense(joueur, inventaire, randomItem);
                }
        }
    }
    public boolean estOuvert() {
        return ouvert;
    }
    public double getX() { return x; }
    public double getY() { return y; }
}

