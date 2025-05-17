package fr.iut.groupe.terraria.demo.modele.monde;

import fr.iut.groupe.terraria.demo.modele.item.Coffre;
import fr.iut.groupe.terraria.demo.modele.item.equipement.Equipement;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import fr.iut.groupe.terraria.demo.modele.personnage.ennemi.Ennemi;
import fr.iut.groupe.terraria.demo.modele.ressource.Ressource;
import fr.iut.groupe.terraria.demo.modele.Inventaire;
import fr.iut.groupe.terraria.demo.modele.zone.Zone;


import java.util.ArrayList;

public class Monde {
    private ArrayList<Ennemi> listEnnemis;
    private ArrayList<Ressource> listRessources;
    private ArrayList<Coffre> listCoffres;
    private double distanceRecup;
    private ArrayList<Zone> listZones;

    // gestion du jour et de la nuit
    private boolean estNuit;
    private int compteurPas;
    private final int CYCLE = 50; // change après 50 déplacements


    public Monde() {
        this.listEnnemis = new ArrayList<>();
        this.listRessources = new ArrayList<>();
        this.listCoffres = new ArrayList<>();
        this.listZones = new ArrayList<>();
        this.distanceRecup = 5; // recuperer distance 5
        this.estNuit = false;
        this.compteurPas = 0;
    }

    /*
     boucle qui gere les ennemis et leurs comportement
     le premier if c'est pour voir si le personnage est dans la zone des ennemis
     si oui alors il va se deplacer vers le personnage
     et si il est assez proche du personnage il le met des degats
    */
    public void mettreAJour(Joueur joueur) {
        for (Ennemi ennemi : listEnnemis) {
            if (ennemi.estProcheDe(joueur)) {
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
            double d = Maths.distance(joueur.getX(), joueur.getY(), r.getX(), r.getY());
            if (d <= distanceRecup && r.estRecoltable()) {
                proches.add(r);
            }
        }
        return proches;
    }

    // ajoute la list dans son inventaire et reduit la durabilité de l'arme.
    public void collecterRessourcesProches(Joueur joueur, Inventaire inventaire) {
        Equipement outil = joueur.getEquipementActuel();

        ArrayList<Ressource> proches = getRessourcesProches(joueur);

        for (Ressource r : proches) {
            if (r.peutEtreRecolteeAvec(outil)) {
                // Ajoute les items à l'inventaire
                for (int i = 0; i < r.getQuantite(); i++) {
                    inventaire.ajouterItem(r.getItemProduit());
                }
                r.recolter();

                if (outil != null) {
                    outil.utiliser();
                    if (outil.estCasse()) {
                        System.out.println("Ton " + outil.getNom() + " est cassé pendant la récolte !");
                        joueur.setEquipementActuel(null); // ou retirer de l’inventaire
                    }
                }
            }
        }
    }
    public void verifierCoffresProches(Joueur joueur, Inventaire inventaire) {
        for (Coffre coffre : listCoffres) {
            coffre.interactionAvecCoffre(joueur, inventaire);
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

    public void mettreAJourCycle() {
        compteurPas++;
        if (compteurPas >= CYCLE) {
            estNuit = !estNuit; // bascule jour ↔ nuit
            compteurPas = 0;
        }
    }
    public void appliquerEffetsZones(Joueur joueur) {
        for (Zone zone : listZones) {
            zone.appliquerEffet(joueur);
        }
    }
    // Ajouter un ennemi
    public void ajouterEnnemi(Ennemi ennemi) {
        listEnnemis.add(ennemi);
    }
    public void ajouterRessource(Ressource ressource) {
        listRessources.add(ressource);
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
