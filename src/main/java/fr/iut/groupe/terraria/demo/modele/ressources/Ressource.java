package fr.iut.groupe.terraria.demo.modele.ressources;

public abstract class Ressource {
    protected String nom;

    public Ressource(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public abstract void utiliser();

    @Override
    public String toString() {
        return "Ressource{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
