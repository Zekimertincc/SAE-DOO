package fr.iut.groupe.terraria.demo.modele.personnage;

public class Quete {
    private String nom;
    private String description;
    private boolean terminee;

    public Quete(String nom, String description) {
        this.nom = nom;
        this.description = description;
        this.terminee = false;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public boolean estTerminee() {
        return terminee;
    }

    public void terminer() {
        terminee = true;
    }

    @Override
    public String toString() {
        return nom + " : " + (terminee ? "Terminée" : "En cours") + " - " + description;
    }
}

