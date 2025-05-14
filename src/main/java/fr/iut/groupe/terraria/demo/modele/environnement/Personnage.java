package fr.iut.groupe.terraria.demo.modele.environnement;


public abstract class Personnage {
    protected String nom;
    protected int pointsDeVie;

    public Personnage(String nom, int pointsDeVie) {
        this.nom = nom;
        this.pointsDeVie = pointsDeVie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPointsDeVie() {
        return pointsDeVie;
    }

    public void setPointsDeVie(int pointsDeVie) {
        this.pointsDeVie = pointsDeVie;
    }

    public boolean estVivant() {
        return pointsDeVie > 0;
    }

    public abstract void agir();

    @Override
    public String toString() {
        return "Personnage{" +
                "nom='" + nom + '\'' +
                ", pointsDeVie=" + pointsDeVie +
                '}';
    }
}
