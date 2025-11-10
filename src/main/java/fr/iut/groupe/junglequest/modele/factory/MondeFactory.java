package fr.iut.groupe.junglequest.modele.factory;

import fr.iut.groupe.junglequest.modele.Environnement;

import java.io.IOException;

/**
 * Factory pour la création et l'initialisation du monde de jeu.
 * 
 * Pattern Factory:
 * - Encapsule la logique de création du monde
 * - Simplifie l'initialisation pour le contrôleur
 * - Centralise la configuration initiale
 * 
 * Architecture MVC:
 * - Cette factory crée le Model (Environnement)
 * - Le contrôleur utilise cette factory
 * - La factory NE crée PAS d'éléments visuels (responsabilité de la Vue)
 * 
 * Note: Cette factory respecte le principe Factory en créant/initialisant
 * des objets, sans dépendre directement de leurs implémentations.
 */
public class MondeFactory {
    
    /**
     * Crée et initialise l'environnement de jeu complet.
     * 
     * Cette méthode:
     * 1. Obtient l'instance de l'environnement (Singleton)
     * 2. Initialise le monde avec la carte par défaut
     * 3. Crée tous les éléments du jeu (joueur, NPCs, ennemis, ressources)
     * 
     * @return L'environnement initialisé et prêt à l'emploi
     * @throws IOException Si la carte ne peut pas être chargée
     */
    public static Environnement creerMonde() throws IOException {
        return creerMonde("/fr/iut/groupe/junglequest/cartes/jungle_map_calque1.csv");
    }
    
    /**
     * Crée et initialise l'environnement de jeu avec une carte spécifique.
     * 
     * @param cheminCarte Chemin vers le fichier CSV de la carte
     * @return L'environnement initialisé
     * @throws IOException Si la carte ne peut pas être chargée
     */
    public static Environnement creerMonde(String cheminCarte) throws IOException {
        System.out.println("===== Création du monde via MondeFactory =====");
        
        // Récupération de l'instance de l'environnement (Singleton)
        Environnement env = Environnement.getInstance();
        
        // Initialisation du monde
        // L'environnement se charge de créer:
        // - La carte (données de terrain)
        // - Le joueur
        // - Les NPCs (Guide, Forgeron)
        // - Les ennemis (Loup)
        // - Les ressources (Arbres, Roches, etc.)
        env.initialiserMonde(cheminCarte);
        
        System.out.println("===== Monde créé avec succès =====");
        return env;
    }
    
    /**
     * Crée un nouveau monde de jeu (réinitialise l'environnement).
     * Utile pour démarrer une nouvelle partie.
     * 
     * @return Un nouvel environnement initialisé
     * @throws IOException Si la carte ne peut pas être chargée
     */
    public static Environnement creerNouveauMonde() throws IOException {
        // Réinitialisation de l'environnement
        Environnement.reset();
        return creerMonde();
    }
    
    /**
     * Crée un monde de test avec configuration simplifiée.
     * Utile pour les tests unitaires.
     * 
     * @return Un environnement de test
     */
    public static Environnement creerMondeTest() {
        try {
            Environnement.reset();
            return creerMonde();
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du monde de test: " + e.getMessage());
            return Environnement.getInstance();
        }
    }
}
