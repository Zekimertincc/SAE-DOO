/*package fr.iut.groupe.terraria.demo.modele.monde;

import fr.iut.groupe.terraria.demo.modele.item.Block;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import fr.iut.groupe.terraria.demo.modele.item.Coffre;
import fr.iut.groupe.terraria.demo.modele.personnage.ennemi.Ennemi;
import fr.iut.groupe.terraria.demo.modele.ressource.Ressource;
import fr.iut.groupe.terraria.demo.modele.zone.Zone;
import fr.iut.groupe.terraria.demo.modele.item.BlockPlace;



import java.util.ArrayList;

public class Monde {
    private ArrayList<Ennemi> listEnnemis;
    private ArrayList<Ressource> listRessources;
    private ArrayList<Coffre> listCoffres;
    private ArrayList<Zone> listZones;
    private ArrayList<BlockPlace> listBlock;

    // gestion du jour et de la nuit
    private boolean estNuit;
    private int compteurPas;
    private final int CYCLE = 50; // change après 50 déplacements


    public Monde() {
        this.listEnnemis = new ArrayList<>();
        this.listRessources = new ArrayList<>();
        this.listCoffres = new ArrayList<>();
        this.listZones = new ArrayList<>();
        this.listBlock = new ArrayList<>();

        this.estNuit = false;
        this.compteurPas = 0;
    }

    public void verifierCoffresProches(Joueur joueur) {
        for (Coffre coffre : listCoffres) {
            coffre.interactionAvecCoffre(joueur);
        }
    }
    public void ajouterBlock (BlockPlace block) {
        listBlock.add(block);
    }
    public boolean retirerBlock() {
        boolean retirer = false;
        for (int i = listBlock.size() - 1; i >= 0; i--) {
            if (listBlock.get(i).getVie() <= 0) {
                listBlock.remove(i);
                retirer = true;
            }
        }
        return retirer;
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

    public void ajouterEnnemi(Ennemi ennemi) {
        listEnnemis.add(ennemi);
    }
    public void ajouterRessource(Ressource ressource) {
        listRessources.add(ressource);
    }
    public void supprimerEnnemi(Ennemi ennemi) {
        listEnnemis.remove(ennemi);
    }

// ----------------------------------------------------------------------------------------------------------------------

    // Retourne la liste des ennemis
    public ArrayList<Ennemi> getListEnnemis() {
        return listEnnemis;
    }
    public ArrayList<Ressource> getListRessources() {
        return listRessources;
    }
    public ArrayList<BlockPlace> getListBlock() {
        return listBlock;
    }

}
*/