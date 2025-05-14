package fr.iut.groupe.terraria.demo.modele.Personnage.ennemi;

import fr.iut.groupe.terraria.demo.modele.Personnage.Joueur;
import fr.iut.groupe.terraria.demo.modele.Personnage.Personnage;

public class Ennemi extends Personnage {
    private String typeDeplacement;
    private String attaque; // "morsure", "coup de poing", etc...

    public Ennemi(double x, double y, int vieMax, int degats, String typeDeplacement, String attaque) {
        super(x, y, vieMax, vieMax, degats);
        this.typeDeplacement = typeDeplacement;
        this.attaque = attaque;
    }

    public String getTypeDeplacement() {
        return typeDeplacement;
    }

    public String getAttaque() {
        return attaque;
    }

    public void attaquer(Joueur joueur) {
        joueur.subirDegats(this.degats);
    }

    /* avance de 1 x vers le joueur
        Cette fonction est à utiliser que quand le personnage est dans la zone d'un ou plusieurs enemis
     */
    public void seDeplacerVers(Joueur joueur) {
        if (joueur.getX() > this.x) {
            this.x += 1;
        } else if (joueur.getX() < this.x) {
            this.x -= 1;
        }
    }
    // regarde si le personnage est dans la zone, si oui true
    public boolean estDansZone(double xMin, double xMax) {
        return this.x >= xMin && this.x <= xMax;
    }

}
