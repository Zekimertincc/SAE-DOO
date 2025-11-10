package fr.iut.groupe.junglequest.vue.animation;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import fr.iut.groupe.junglequest.vue.utilitaire.ExtracteurSprites;

import java.io.IOException;
import java.io.InputStream;

/**
 * Gestionnaire centralisé des animations du jeu (View Layer - MVC)
 * 
 * Architecture:
 * - Cette classe appartient à la Vue (pas de logique métier)
 * - Charge et gère toutes les animations des sprites
 * - Simplifie l'accès aux frames d'animation
 * 
 * Responsabilités:
 * - Charger les spritesheets depuis les ressources
 * - Extraire les frames individuelles
 * - Fournir un accès centralisé aux animations
 * 
 * Avantages:
 * - Évite de passer 13 tableaux WritableImage[] en paramètres
 * - Centralise la logique de chargement
 * - Facilite l'ajout de nouvelles animations
 */
public class AnimationManager {
    
    // Animations du joueur
    private WritableImage[] idle;
    private WritableImage[] marche;
    private WritableImage[] attaque;
    private WritableImage[] preparationSaut;
    private WritableImage[] volSaut;
    private WritableImage[] sautReload;
    private WritableImage[] chute;
    private WritableImage[] atterrissage;
    private WritableImage[] degats;
    private WritableImage[] mort;
    private WritableImage[] sort;
    private WritableImage[] accroupi;
    private WritableImage[] bouclier;
    
    /**
     * Constructeur privé - utiliser create() pour la création
     */
    private AnimationManager() {
    }
    
    /**
     * Crée et initialise un AnimationManager avec toutes les animations du joueur.
     * 
     * @param cheminSpritesheet Chemin vers le spritesheet du joueur
     * @return AnimationManager initialisé
     * @throws IOException Si le spritesheet ne peut pas être chargé
     */
    public static AnimationManager create(String cheminSpritesheet) throws IOException {
        AnimationManager manager = new AnimationManager();
        manager.chargerAnimationsJoueur(cheminSpritesheet);
        return manager;
    }
    
    /**
     * Charge toutes les animations du joueur depuis le spritesheet.
     * 
     * @param cheminSpritesheet Chemin vers le spritesheet
     * @throws IOException Si le fichier ne peut pas être chargé
     */
    private void chargerAnimationsJoueur(String cheminSpritesheet) throws IOException {
        InputStream stream = getClass().getResourceAsStream(cheminSpritesheet);
        if (stream == null) {
            throw new IOException("Spritesheet introuvable: " + cheminSpritesheet);
        }
        
        Image spritesheet = new Image(stream);
        
        // Extraction de toutes les animations
        this.idle = ExtracteurSprites.idle(spritesheet);
        this.marche = ExtracteurSprites.marche(spritesheet);
        this.attaque = ExtracteurSprites.attaque(spritesheet);
        this.preparationSaut = ExtracteurSprites.preparationSaut(spritesheet);
        this.volSaut = ExtracteurSprites.volSaut(spritesheet);
        this.sautReload = ExtracteurSprites.sautReload(spritesheet);
        this.chute = ExtracteurSprites.chute(spritesheet);
        this.atterrissage = ExtracteurSprites.atterrissage(spritesheet);
        this.degats = ExtracteurSprites.degats(spritesheet);
        this.mort = ExtracteurSprites.mort(spritesheet);
        this.sort = ExtracteurSprites.sort(spritesheet);
        this.accroupi = ExtracteurSprites.accroupi(spritesheet);
        this.bouclier = ExtracteurSprites.bouclier(spritesheet);
        
        System.out.println("Animations du joueur chargées: 13 séquences");
    }
    
    // ========== Getters pour les animations ==========
    
    public WritableImage[] getIdle() {
        return idle;
    }
    
    public WritableImage[] getMarche() {
        return marche;
    }
    
    public WritableImage[] getAttaque() {
        return attaque;
    }
    
    public WritableImage[] getPreparationSaut() {
        return preparationSaut;
    }
    
    public WritableImage[] getVolSaut() {
        return volSaut;
    }
    
    public WritableImage[] getSautReload() {
        return sautReload;
    }
    
    public WritableImage[] getChute() {
        return chute;
    }
    
    public WritableImage[] getAtterrissage() {
        return atterrissage;
    }
    
    public WritableImage[] getDegats() {
        return degats;
    }
    
    public WritableImage[] getMort() {
        return mort;
    }
    
    public WritableImage[] getSort() {
        return sort;
    }
    
    public WritableImage[] getAccroupi() {
        return accroupi;
    }
    
    public WritableImage[] getBouclier() {
        return bouclier;
    }
    
    /**
     * Obtient le nombre de frames pour l'animation d'atterrissage.
     * Utile pour les timers d'animation.
     * 
     * @return Nombre de frames
     */
    public int getNbFramesAtterrissage() {
        return atterrissage != null ? atterrissage.length : 0;
    }
}

