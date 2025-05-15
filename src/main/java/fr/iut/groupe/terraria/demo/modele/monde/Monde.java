package fr.iut.groupe.terraria.demo.modele.monde;

import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import fr.iut.groupe.terraria.demo.modele.personnage.ennemi.Ennemi;
import fr.iut.groupe.terraria.demo.modele.ressource.Ressource;
import fr.iut.groupe.terraria.demo.modele.Inventaire;


import java.util.ArrayList;

public class Monde {
    private ArrayList<Ennemi> listEnnemis;
    private ArrayList<Ressource> listRessources;
    private double distanceRecup;

    public Monde() {
        listEnnemis = new ArrayList<>();
        listRessources = new ArrayList<>();
        this.distanceRecup = 5;
    }

    // Ajouter un ennemi
    public void ajouterEnnemi(Ennemi ennemi) {
        listEnnemis.add(ennemi);
    }
    public void ajouterRessource(Ressource ressource) {
        listRessources.add(ressource);
    }
    /*
     boucle qui gere les ennemis et leurs comportement
     le premier if c'est pour voir si le personnage est dans la zone des ennemis
     si oui alors il va se deplacer vers le personnage
     et si il est assez proche du personnage il le met des degats
    */
    public void mettreAJour(Joueur joueur) {
        for (Ennemi ennemi : listEnnemis) {
            if (ennemi.estProcheDe(joueur, 50)) {
                ennemi.seDeplacerVers(joueur);
            }
            if (ennemi.estProcheDe(joueur, 10)) {
                ennemi.attaquer(joueur);
            }
        }
    }
    // recupere toutes les ressources proches du joueur et met ca dans une list temporaire (pas dans son inventaire encore)
    public ArrayList<Ressource> getRessourcesProches(Joueur joueur) {
        ArrayList<Ressource> proches = new ArrayList<>();
        for (Ressource r : listRessources) {
            double dx = r.getX() - joueur.getX();
            double dy = r.getY() - joueur.getY();
            double d = Math.sqrt(dx * dx + dy * dy);
            if (d <= distanceRecup && r.estRecoltable()) {
                proches.add(r);
            }
        }
        return proches;
    }

    // ajoute la list dans son inventaire
    public void collecterRessourcesProches(Joueur joueur, Inventaire inventaire) {
        ArrayList<Ressource> ressourcesProches = getRessourcesProches(joueur);
        for (Ressource ressource : ressourcesProches) {
            for (int i = 0; i < ressource.getQuantite(); i++) {
                inventaire.ajouterItem(ressource.getItemProduit());
            }
            ressource.recolter();
        }
    }

    // Supprimer un ennemi
    public void supprimerEnnemi(Ennemi ennemi) {
        listEnnemis.remove(ennemi);
    }

    // Vérifie si tous les ennemis sont morts
    public boolean tousLesEnnemisSontMorts() {
        for (Ennemi e : listEnnemis) {
            if (!e.estMort()) return false;
        }
        return true;
    }

    // Retourne la liste des ennemis
    public ArrayList<Ennemi> getListEnnemis() {
        return listEnnemis;
    }

    public ArrayList<Ressource> getListRessources() {
        return listRessources;
    }

    // Afficher les ennemis (console)
    public void afficherEnnemis() {
        for (Ennemi e : listEnnemis) {
            System.out.println("Ennemi : x=" + e.getX() + " y=" + e.getY() + " PV=" + e.getVie());
        }
    }

}
