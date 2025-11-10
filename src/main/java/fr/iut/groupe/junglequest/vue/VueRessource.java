package fr.iut.groupe.junglequest.vue;

import fr.iut.groupe.junglequest.modele.item.farm.RessourceModele;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.beans.property.DoubleProperty;

/**
 * Vue d'une ressource (View - MVC)
 * 
 * Responsabilités:
 * - Gestion de l'affichage visuel de la ressource (ImageView)
 * - Chargement des images depuis les ressources
 * - Synchronisation avec le modèle (position, état)
 * 
 * Architecture MVC:
 * - La Vue connaît le Model (RessourceModele)
 * - La Vue observe le Model pour les changements
 * - La Vue NE contient PAS de logique métier
 * 
 * Communication:
 * - Binding sur les propriétés de position de la caméra
 * - Mise à jour automatique de la position à l'écran
 */
public class VueRessource {
    private final RessourceModele modele;
    private final ImageView sprite;
    
    /**
     * Constructeur - Crée la vue d'une ressource
     * 
     * @param modele Le modèle de la ressource
     * @param image L'image à afficher
     */
    public VueRessource(RessourceModele modele, Image image) {
        this.modele = modele;
        this.sprite = new ImageView(image);
        
        // Configuration du sprite
        sprite.setFitWidth(modele.getLargeur());
        sprite.setFitHeight(modele.getHauteur());
        sprite.setViewOrder(-1); // Afficher derrière le joueur
        
        // Position initiale
        sprite.setX(modele.getX());
        sprite.setY(modele.getY());
    }
    
    /**
     * Lie la position du sprite à la caméra (scrolling)
     * 
     * @param offsetXProperty Position X de la caméra
     * @param offsetYProperty Position Y de la caméra
     */
    public void lierPosition(DoubleProperty offsetXProperty, DoubleProperty offsetYProperty) {
        // Binding: position à l'écran = position dans le monde - offset caméra
        sprite.xProperty().bind(
            offsetXProperty.negate().add(modele.getX())
        );
        sprite.yProperty().bind(
            offsetYProperty.negate().add(modele.getY())
        );
    }
    
    /**
     * Met à jour la visibilité de la ressource selon son état
     */
    public void mettreAJour() {
        if (modele.estRecoltee()) {
            sprite.setVisible(false);
        }
    }
    
    /**
     * Vérifie si un clic est sur cette ressource
     * 
     * @param xMonde Position X du clic dans le monde
     * @param yMonde Position Y du clic dans le monde
     * @return true si le clic est sur la ressource, false sinon
     */
    public boolean contient(double xMonde, double yMonde) {
        return xMonde >= modele.getX() 
            && xMonde <= modele.getX() + modele.getLargeur()
            && yMonde >= modele.getY()
            && yMonde <= modele.getY() + modele.getHauteur();
    }
    
    // ========== Getters ==========
    
    public ImageView getSprite() {
        return sprite;
    }
    
    public RessourceModele getModele() {
        return modele;
    }
}

