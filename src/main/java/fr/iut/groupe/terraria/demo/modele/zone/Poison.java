package fr.iut.groupe.terraria.demo.modele.zone;

import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;

public class Poison extends Zone{
    private int degats; // en points de vie du joeur à retirer (en coeur)
    public Poison(double x, double y, double largeur, double hauteur, int degats) {
        super(x, y, largeur, hauteur);
        this.degats = 1;
    }

    // regarde si le joueur est dans la zone (qui est definit dans le constructeur) puis enleve un coeur pour chaque appelle de cette fonction.
    public void appliquerEffet(Joueur joueur) {
        if (estJoueurDansZone(joueur)) {
            joueur.subirDegats(degats);
        }
    }
}
