package fr.iut.groupe.terraria.demo.modele.environnement;

import fr.iut.groupe.terraria.demo.modele.Joueur;
import fr.iut.groupe.terraria.demo.modele.ressources.Ressource;


import java.util.ArrayList;
import java.util.List;

public class Monde {
    private List<Personnage> personnages;
    private List<Ressource> ressources;
    private List<Tuile> map;
    private Joueur joueur;

    public Monde(Joueur joueur) {
        this.joueur = joueur;
        this.personnages = new ArrayList<>();
        this.ressources = new ArrayList<>();
        this.map = new ArrayList<>();
    }

    public List<Personnage> getPersonnages() {
        return personnages;
    }

    public void ajouterPersonnage(Personnage personnage) {
        this.personnages.add(personnage);
    }

    public List<Ressource> getRessources() {
        return ressources;
    }

    public void ajouterRessource(Ressource ressource) {
        this.ressources.add(ressource);
    }

    public List<Tuile> getMap() {
        return map;
    }

    public void ajouterTuile(Tuile tuile) {
        this.map.add(tuile);
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    @Override
    public String toString() {
        return "Monde{" +
                "personnages=" + personnages +
                ", ressources=" + ressources +
                ", map=" + map +
                ", joueur=" + joueur +
                '}';
    }
}
