package fr.iut.groupe.junglequest.modele.personnages.factory;

import fr.iut.groupe.junglequest.modele.personnages.*;

/**
 * Implémentation concrète de PersonnageFactory (Factory Pattern)
 * 
 * Architecture MVC:
 * - Crée uniquement des objets du Model
 * - NE crée PAS d'éléments visuels (no Image)
 * - Les personnages créés sont indépendants de l'affichage
 * 
 * Responsabilités:
 * - Créer les instances de personnages avec configuration par défaut
 * - Garantir la cohérence des paramètres de création
 */
public class DefaultPersonnageFactory implements PersonnageFactory {

    /**
     * Crée un joueur avec paramètres par défaut
     */
    @Override
    public Joueur createJoueur(double x, double y) {
        Joueur joueur = new Joueur(x, y);
        joueur.setEstAuSol(true);
        return joueur;
    }

    /**
     * Crée un loup ennemi avec ses caractéristiques
     */
    @Override
    public Loup createLoup(double x, double y, double largeur, double hauteur, int damage) {
        Loup loup = new Loup(x, y, largeur, hauteur, damage);
        loup.setEstAuSol(true);
        return loup;
    }

    /**
     * Crée un guide NPC
     */
    @Override
    public Guide createGuide(double x, double y) {
        Guide guide = new Guide(x, y);
        guide.setEstAuSol(true);
        return guide;
    }

    /**
     * Crée un forgeron NPC
     */
    @Override
    public Forgeron createForgeron(double x, double y) {
        Forgeron forgeron = new Forgeron(x, y);
        forgeron.setEstAuSol(true);
        return forgeron;
    }
}
