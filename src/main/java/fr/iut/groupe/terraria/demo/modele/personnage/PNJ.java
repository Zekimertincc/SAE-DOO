package fr.iut.groupe.terraria.demo.modele.personnage;

import fr.iut.groupe.terraria.demo.modele.monde.Maths;

public class PNJ extends PersonnageJeu{
    private String nom;
    private Quete quete;

    public PNJ(String nom, double x, double y, Quete quete) {
        super(x, y, 10000,1000,  0);
        this.nom = nom;
        this.quete = quete;
    }

    // retourne true si un enemi est proche du joueur (distance de 20)
    public boolean estProcheDe(Joueur joueur) {
        double d = Maths.distance(joueur.getX(), joueur.getY(), this.getX(), this.getY());
        return d < 20;
    }

    public String getNom() {
        return nom;
    }
    public Quete getQuete() {
        return quete;
    }
}

