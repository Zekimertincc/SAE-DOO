package fr.iut.groupe.terraria.demo.modele.personnage;

import fr.iut.groupe.terraria.demo.modele.Ciblable;
import fr.iut.groupe.terraria.demo.modele.Inventaire;
import fr.iut.groupe.terraria.demo.modele.item.Block;
import fr.iut.groupe.terraria.demo.modele.item.Item;
import fr.iut.groupe.terraria.demo.modele.item.equipement.Couteau;
import fr.iut.groupe.terraria.demo.modele.item.equipement.Equipement;
import fr.iut.groupe.terraria.demo.modele.monde.Maths;
import fr.iut.groupe.terraria.demo.modele.monde.Environnement;
import fr.iut.groupe.terraria.demo.modele.ressource.Ressource;

public class Joueur extends Personnage {
    private boolean enLair = false;
    private final int tileSize = 32;
    private final int[][] map;

    private Equipement equipementActuel;
    private Inventaire inventaire;
    private EtatTemporaire etatTemporaire;

    public Joueur(double x, double y, int vieMax, EtatTemporaire etatTemporaire, int[][] map) {
        super(x, y, 0.5, 0, vieMax, vieMax, 0);
        this.equipementActuel = new Couteau();
        this.inventaire = new Inventaire();
        this.etatTemporaire = etatTemporaire;
        this.map = map;
    }

    /** Déplacement gauche avec détection de collision */
    public void gauche() {
        double nextX = getX() - vitesseX;
        if (!collisionHorizontale(nextX)) {
            setX(Math.max(0, nextX));
        }
    }

    /** Déplacement droite avec détection de collision */
    public void droite() {
        double nextX = getX() + vitesseX;
        int maxX = map[0].length * tileSize - getLargeur();
        if (!collisionHorizontale(nextX)) {
            setX(Math.min(maxX, nextX));
        }
    }

    /** Fait sauter le joueur si pas déjà en l'air */
    public void sauter() {
        if (!enLair) {
            vitesseY = -10;
            enLair = true;
        }
    }

    /** Applique la gravité et détecte collisions avec le sol */
    public void appliquerGravite() {
        vitesseY += 0.5;
        double nextY = getY() + vitesseY;

        int leftTile  = (int) getX() / tileSize;
        int rightTile = (int) (getX() + getLargeur() - 1) / tileSize;
        int footTile  = (int) (nextY + getHauteur()) / tileSize;

        boolean collisionSol = false;
        for (int tx = leftTile; tx <= rightTile; tx++) {
            if (footTile < 0 || footTile >= map.length || tx < 0 || tx >= map[0].length) continue;
            if (map[footTile][tx] == 1) {
                collisionSol = true;
                break;
            }
        }

        if (collisionSol) {
            vitesseY = 0;
            enLair = false;
            setY(footTile * tileSize - getHauteur());
        } else {
            setY(nextY);
            enLair = true;
        }
    }

    /** Collision horizontale gauche/droite */
    private boolean collisionHorizontale(double nextX) {
        int topTile = (int) getY() / tileSize;
        int bottomTile = (int) (getY() + getHauteur() - 1) / tileSize;
        int leftTile = (int) nextX / tileSize;
        int rightTile = (int) (nextX + getLargeur() - 1) / tileSize;

        for (int ty = topTile; ty <= bottomTile; ty++) {
            if (leftTile >= 0 && leftTile < map[0].length && ty >= 0 && ty < map.length) {
                if (map[ty][leftTile] == 1) return true;
            }
            if (rightTile >= 0 && rightTile < map[0].length && ty >= 0 && ty < map.length) {
                if (map[ty][rightTile] == 1) return true;
            }
        }
        return false;
    }

    /** Casser une ressource et l’ajouter à l’inventaire */
    public void casserRessource(Ressource r) {
        System.out.println("Tu as cassé: " + r.getNom());
        Item item = r.getItemProduit();
        boolean ok = inventaire.ajouterItem(item);

        if (ok)
            System.out.println(item.getNom() + " ajouté à l'inventaire");
        else
            System.out.println("Inventaire plein pour: " + item.getNom());

        inventaire.afficherMap();
    }

    /** Utilise l’équipement actif sur une cible */
    public void utiliserEquipementSur(Ciblable cible) {
        if (equipementActuel != null) {
            equipementActuel.degatsContre(this, cible);
        }
    }

    /** Change l’équipement s’il est cassé */
    public void changerNullEquipement() {
        if (this.equipementActuel != null && this.equipementActuel.estCasse()) {
            setEquipementActuel(null);
        }
    }

    /** Subit des dégâts en fonction de l'état temporaire */
    @Override
    public void subirDegats(int degats) {
        if (!this.getEtatTemporaire().isInvincible() && this.getEtatTemporaire().isVulnerable()) {
            degats *= 2;
        }
        super.subirDegats(degats);
    }

    /** Fixe les points de vie à une valeur spécifique */
    public void mettreAPV(int nouvelleVie) {
        this.vie = Math.max(0, Math.min(nouvelleVie, this.vieMax));
    }

    /** Construit un bloc si assez d’items et pas trop proche d’un autre bloc */
    public boolean construireEtPlacerBloc(String type, double x, double y, Environnement monde) {
        if (inventaire.getMapItems().getOrDefault(type, 0) < 2) return false;

        for (Block b : monde.getListBlock()) {
            if (Maths.distance(b.getX(), b.getY(), x, y) < 32) {
                return false;
            }
        }

        inventaire.retirerItem(type, 2);
        Block block = new Block(type);
        block.placer(x, y);
        monde.ajouterBlock(block);
        return true;
    }

    // GETTERS / SETTERS

    public Equipement getEquipementActuel() { return equipementActuel; }
    public void setEquipementActuel(Equipement equipement) { this.equipementActuel = equipement; }

    public double getVitesseY() { return vitesseY; }

    public EtatTemporaire getEtatTemporaire() { return etatTemporaire; }
    public void setEtatTemporaire(EtatTemporaire etatTemporaire) { this.etatTemporaire = etatTemporaire; }

    public Inventaire getInventaire() { return inventaire; }

    public int getLargeur() { return 64; }
    public int getHauteur() { return 96; }
}
