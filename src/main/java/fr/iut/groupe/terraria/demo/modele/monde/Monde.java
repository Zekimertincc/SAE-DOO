package fr.iut.groupe.terraria.demo.modele.monde;

import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import fr.iut.groupe.terraria.demo.modele.personnage.ennemi.Ennemi;

import java.util.ArrayList;
import java.util.Iterator;

public class Monde {
    private ArrayList<Ennemi> ennemis;

    public Monde() {
        ennemis = new ArrayList<>();
    }

    // Ajouter un ennemi
    public void ajouterEnnemi(Ennemi ennemi) {
        ennemis.add(ennemi);
    }

    /*
     boucle qui gere les ennemis et leurs comportement
     le premier if c'est pour voir si le personnage est dans la zone des ennemis
     si oui alors il va se deplacer vers le personnage
     et si il est assez proche du personnage il le met des degats
    */
    public void mettreAJour(Joueur joueur) {
        for (Ennemi ennemi : ennemis) {
            if (ennemi.estProcheDe(joueur, 50)) {
                ennemi.seDeplacerVers(joueur);
            }
            if (ennemi.estProcheDe(joueur, 10)) {
                ennemi.attaquer(joueur);
            }
        }
    }
    // Supprimer un ennemi
    public void supprimerEnnemi(Ennemi ennemi) {
        ennemis.remove(ennemi);
    }

    // Vérifie si tous les ennemis sont morts
    public boolean tousLesEnnemisSontMorts() {
        for (Ennemi e : ennemis) {
            if (!e.estMort()) return false;
        }
        return true;
    }

    // Mettre à jour les ennemis
    public void mettreAJour(Joueur joueur) {
        Iterator<Ennemi> iterator = ennemis.iterator();
        while (iterator.hasNext()) {
            Ennemi ennemi = iterator.next();

            if (ennemi.estMort()) {
                iterator.remove(); // Supprime l'ennemi mort
                continue;
            }

            if (ennemi.estDansZone(ennemi.getX() - 50, ennemi.getX() + 50)) {
                if (ennemi.estProcheDe(joueur, 100)) {
                    ennemi.seDeplacerVers(joueur); // Rapprochement
                }
                if (ennemi.estProcheDe(joueur, 10)) {
                    ennemi.attaquer(joueur); // Attaque
                }
            }
        }
    }

    // Retourne la liste des ennemis
    public ArrayList<Ennemi> getEnnemis() {
        return ennemis;
    }

    // Afficher les ennemis (console)
    public void afficherEnnemis() {
        for (Ennemi e : ennemis) {
            System.out.println("Ennemi : x=" + e.getX() + " y=" + e.getY() + " PV=" + e.getVie());
        }
    }

}

