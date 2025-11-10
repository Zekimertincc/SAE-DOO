package fr.iut.groupe.junglequest.modele.personnages.factory;

import fr.iut.groupe.junglequest.modele.personnages.*;

/**
 * Factory interface for creating game characters (Factory Pattern)
 * 
 * Architecture MVC:
 * - Cette factory crée des objets du Model uniquement
 * - Les objets créés NE contiennent PAS d'éléments visuels (no ImageView, no Image)
 * - Les éléments visuels sont créés séparément dans la Vue
 * 
 * Responsabilités:
 * - Encapsuler la logique de création des personnages
 * - Simplifier l'instanciation pour les clients
 * - Permettre l'extension facile (nouveaux types de personnages)
 */
public interface PersonnageFactory {
    /**
     * Crée un joueur
     * 
     * @param x Position X initiale
     * @param y Position Y initiale
     * @return Une nouvelle instance de Joueur
     */
    Joueur createJoueur(double x, double y);

    /**
     * Crée un loup ennemi
     * 
     * @param x Position X initiale
     * @param y Position Y initiale
     * @param largeur Largeur du sprite
     * @param hauteur Hauteur du sprite
     * @param damage Dégâts infligés par le loup
     * @return Une nouvelle instance de Loup
     */
    Loup createLoup(double x, double y, double largeur, double hauteur, int damage);

    /**
     * Crée un guide NPC
     * 
     * @param x Position X initiale
     * @param y Position Y initiale
     * @return Une nouvelle instance de Guide
     */
    Guide createGuide(double x, double y);

    /**
     * Crée un forgeron NPC
     * 
     * @param x Position X initiale
     * @param y Position Y initiale
     * @return Une nouvelle instance de Forgeron
     */
    Forgeron createForgeron(double x, double y);
}