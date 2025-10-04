package fr.iut.groupe.junglequest.modele.item.farm;

import javafx.scene.image.ImageView;

/**
 * Représente une ressource présente sur la carte (arbre, canne, roche...).
 * Chaque ressource est associée à un nom interne et à l'objet obtenu
 * lors de sa destruction.
 */
public class Ressource {
    private final String nom;          // ex: "Arbre", "Canne", "Roche"
    private final String itemRecompense; // ex: "Bois", "Pierre"
    private final ImageView sprite;
    private final double x;
    private final double y;

    public Ressource(String nom, String itemRecompense, ImageView sprite, double x, double y) {
        this.nom = nom;
        this.itemRecompense = itemRecompense;
        this.sprite = sprite;
        this.x = x;
        this.y = y;
    }

    public String getNom() {
        return nom;
    }

    public String getItemRecompense() {
        return itemRecompense;
    }

    public ImageView getSprite() {
        return sprite;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
