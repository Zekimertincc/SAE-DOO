package fr.iut.groupe.terraria.demo.modele;

import javafx.scene.image.Image;

/**
 * Représente un tileset chargé depuis un fichier .tsx.
 */
public class Tileset {

    private final int firstgid;       // Premier ID de tuile dans ce tileset
    private final int tileWidth;      // Largeur d'une tuile
    private final int tileHeight;     // Hauteur d'une tuile
    private final int columns;        // Nombre de colonnes dans l'image
    private final Image image;        // Image du tileset

    public Tileset(int firstgid, int tileWidth, int tileHeight, int columns, Image image) {
        this.firstgid = firstgid;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.columns = columns;
        this.image = image;
    }

    // Getters
    public int getFirstgid() {
        return firstgid;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getColumns() {
        return columns;
    }

    public Image getImage() {
        return image;
    }
}
