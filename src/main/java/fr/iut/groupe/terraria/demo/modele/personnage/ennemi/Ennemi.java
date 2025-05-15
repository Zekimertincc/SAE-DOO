package fr.iut.groupe.terraria.demo.modele.personnage.ennemi;

import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import fr.iut.groupe.terraria.demo.modele.personnage.PersonnageJeu;

public class Ennemi extends PersonnageJeu {
    private String typeDeplacement;
    private String attaque; // "morsure", "coup de poing", etc...

    public Ennemi(double x, double y, int vieMax, int degats, String typeDeplacement, String attaque) {
        super(x, y, vieMax, vieMax, degats);
        this.typeDeplacement = typeDeplacement;
        this.attaque = attaque;
    }

    public void attaquer(Joueur joueur) {
        joueur.subirDegats(this.degats);
    }
    // retourne true si un enemi est proche du joueur (distance de 20)
    public boolean estProcheDe(Joueur joueur) {
        double dx = this.x - joueur.getX();
        double dy = this.y - joueur.getY();
        double d = Math.sqrt(dx * dx + dy * dy);
        return d < 50;
    }
    // surcharge
    public boolean estProcheDe(Joueur joueur, int distance) {
        double dx = this.x - joueur.getX();
        double dy = this.y - joueur.getY();
        double d = Math.sqrt(dx * dx + dy * dy);
        return d < distance;
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

    public String getTypeDeplacement() {
        return typeDeplacement;
    }

    public String getAttaque() {
        return attaque;
    }

}
