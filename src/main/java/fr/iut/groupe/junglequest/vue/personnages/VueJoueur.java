package fr.iut.groupe.junglequest.vue.personnages;

import fr.iut.groupe.junglequest.modele.personnages.Joueur;

import javafx.scene.image.ImageView;

// Vue graphique associée au joueur.
// Elle détient uniquement le sprite et fait le lien avec le modèle Joueur.
public class VueJoueur {
    private final Joueur joueur;
    private final ImageView sprite;

    public VueJoueur(Joueur joueur, ImageView sprite) {
        this.joueur = joueur;
        this.sprite = sprite;
    }

    // Retourne le noeud graphique représentant le joueur.
    public ImageView getSprite() {
        return sprite;
    }

   
    // La position est maintenant gérée directement par VueJeuController.

    
    // Largeur du sprite utilisée pour les collisions.
    public double getLargeur() {
        return sprite.getFitWidth();
    }

    // Hauteur du sprite utilisée pour les collisions.
    public double getHauteur() {
        return sprite.getFitHeight();
    }
}
