package fr.iut.groupe.terraria.demo.modele.personnage;

public class PNJ {
    private String nom;
    private double x, y;
    private Quete quete;

    public PNJ(String nom, double x, double y, Quete quete) {
        this.nom = nom;
        this.x = x;
        this.y = y;
        this.quete = quete;
    }

    // retourne true si un enemi est proche du joueur mais ne fait rien c'est juste une detection
    public boolean estProcheDuJoueur(Joueur joueur) {
        double dx = joueur.getX() - this.x;
        double dy = joueur.getY() - this.y;
        double d = Math.sqrt(dx * dx + dy * dy);
        return d >= 20;
    }

    public String getNom() {
        return nom;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Quete getQuete() {
        return quete;
    }
}

