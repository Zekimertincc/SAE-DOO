package fr.iut.groupe.junglequest.vue;

import fr.iut.groupe.junglequest.modele.item.farm.RessourceModele;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
// import javafx.beans.property.DoubleProperty; // Supprimé

// Vue d'une ressource (View - MVC)

// Responsabilités:
// - Gestion de l'affichage visuel de la ressource (ImageView)
// - Chargement des images depuis les ressources
// - Synchronisation avec le modèle (position, état)

// Architecture MVC:
// - La Vue connaît le Model (RessourceModele)
// - La Vue observe le Model pour les changements
// - La Vue NE contient PAS de logique métier

public class VueRessource {
    private final RessourceModele modele;
    private final ImageView sprite;
    
    // Constructeur - Crée la vue d'une ressource
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
    
    //
    // SUPPRIMÉ : La méthode lierPosition(...) a été entièrement retirée.
    // La position est maintenant gérée directement par VueJeuController.
    //
    
    // Met à jour la visibilité de la ressource selon son état
    public void mettreAJour() {
        if (modele.estRecoltee()) {
            sprite.setVisible(false);
        }
    }
    
    // Vérifie si un clic est sur cette ressource
    public boolean contient(double xMonde, double yMonde) {
        return xMonde >= modele.getX() 
            && xMonde <= modele.getX() + modele.getLargeur()
            && yMonde >= modele.getY()
            && yMonde <= modele.getY() + modele.getHauteur();
    }
        
    // Retourne le sprite (ImageView)
    public ImageView getSprite() {
        return sprite;
    }
    
    // Retourne le modèle de données associé
    public RessourceModele getModele() {
        return modele;
    }
}
