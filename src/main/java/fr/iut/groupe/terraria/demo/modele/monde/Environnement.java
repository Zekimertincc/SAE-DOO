package fr.iut.groupe.terraria.demo.modele.monde;

import fr.iut.groupe.terraria.demo.modele.item.Block;
import fr.iut.groupe.terraria.demo.modele.item.Coffre;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import fr.iut.groupe.terraria.demo.modele.personnage.ennemi.Ennemi;
import fr.iut.groupe.terraria.demo.modele.ressource.Ressource;
import fr.iut.groupe.terraria.demo.modele.zone.Zone;

import java.util.ArrayList;

public class Environnement {

    private ArrayList<Ennemi> listEnnemis;
    private ArrayList<Ressource> listRessources;
    private ArrayList<Coffre> listCoffres;
    private ArrayList<Zone> listZones;
    private ArrayList<Block> listBlock;

    public Environnement() {
        this.listEnnemis = new ArrayList<>();
        this.listRessources = new ArrayList<>();
        this.listCoffres = new ArrayList<>();
        this.listZones = new ArrayList<>();
        this.listBlock = new ArrayList<>();
    }

    public void appliquerEffetsZones(Joueur joueur) {
        for (Zone zone : listZones) {
            zone.appliquerEffet(joueur);
        }
    }
    public void verifierCoffresProches(Joueur joueur) {
        for (Coffre coffre : listCoffres) {
            coffre.interactionAvecCoffre(joueur);
        }
    }
    public boolean retirerBlocsDetruits() {
        boolean aRetire = false;
        for (int i = listBlock.size() - 1; i >= 0; i--) {
            if (listBlock.get(i).estDetruit()) {
                listBlock.remove(i);
                aRetire = true;
            }
        }
        return aRetire;
    }
    public boolean tousLesEnnemisSontMorts() {
        for (Ennemi e : listEnnemis) {
            if (!e.estMort()) return false;
        }
        return true;
    }

// ----------------------------------------------------------------------------------------------------------------

    public void ajouterEnnemi(Ennemi ennemi) {
        listEnnemis.add(ennemi);
    }
    public void supprimerEnnemi(Ennemi ennemi) {
        listEnnemis.remove(ennemi);
    }
    public void ajouterRessource(Ressource r) {
        listRessources.add(r);
    }
    public void ajouterBlock(Block b) {
        listBlock.add(b);
    }
    public void ajouterZone(Zone z) {
        listZones.add(z);
    }
    public void ajouterCoffre(Coffre c) {
        listCoffres.add(c);
    }
    public ArrayList<Ennemi> getListEnnemis() {
        return listEnnemis;
    }
    public ArrayList<Ressource> getListRessources() {
        return listRessources;
    }
    public ArrayList<Coffre> getListCoffres() {
        return listCoffres;
    }
    public ArrayList<Zone> getListZones() {
        return listZones;
    }
    public ArrayList<Block> getListBlock() {
        return listBlock;
    }
}
