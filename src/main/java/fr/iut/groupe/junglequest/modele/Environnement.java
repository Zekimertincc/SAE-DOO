package fr.iut.groupe.junglequest.modele;

import fr.iut.groupe.junglequest.modele.carte.Carte;
import fr.iut.groupe.junglequest.modele.carte.ChargeurCarte;
import fr.iut.groupe.junglequest.modele.item.farm.RessourceModele;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;
import fr.iut.groupe.junglequest.modele.personnages.Loup;
import fr.iut.groupe.junglequest.modele.personnages.Guide;
import fr.iut.groupe.junglequest.modele.personnages.Forgeron;
import fr.iut.groupe.junglequest.modele.personnages.factory.PersonnageFactory;
import fr.iut.groupe.junglequest.modele.personnages.factory.DefaultPersonnageFactory;
import fr.iut.groupe.junglequest.modele.observateurs.SujetObserve;
import fr.iut.groupe.junglequest.modele.observateurs.SujetObserveImpl;
import fr.iut.groupe.junglequest.modele.observateurs.Observateur;
import fr.iut.groupe.junglequest.modele.observateurs.TypeChangement;
import fr.iut.groupe.junglequest.modele.donnees.ConstantesJeu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Environnement du jeu - Singleton Pattern
 * 
 * Responsabilités (MVC - Model):
 * - Gestion centralisée de tous les éléments du monde de jeu
 * - Création et initialisation du terrain (carte)
 * - Création des entités (joueur, NPCs, ennemis, ressources)
 * - Exécution des actions de jeu (attaque, mouvement, interaction)
 * - Notification des observateurs pour synchronisation avec la vue
 * 
 * Architecture:
 * - Le Model NE connaît PAS la Vue
 * - Utilise Observer Pattern pour notifier les changements
 * - La Vue s'abonne aux changements via les observateurs
 * 
 * Pattern Singleton simplifié:
 * - Pas de synchronisation (jeu mono-thread)
 * - Initialisation lazy thread-unsafe (acceptable pour JavaFX)
 */
public class Environnement implements SujetObserve {
    private static Environnement instance;
    
    // Composants du monde
    private Carte carte;
    private Joueur joueur;
    private Loup loup;
    private Guide guide;
    private Forgeron forgeron;
    private List<RessourceModele> ressources;
    
    // Observer pattern pour notification des vues
    private final SujetObserveImpl sujetObservable;
    
    // Factory pour création des personnages
    private final PersonnageFactory personnageFactory;
    
    /**
     * Constructeur privé - Singleton Pattern
     * Initialise uniquement les composants indépendants
     */
    private Environnement() {
        this.ressources = new ArrayList<>();
        this.sujetObservable = new SujetObserveImpl();
        this.personnageFactory = new DefaultPersonnageFactory();
    }

    /**
     * Obtient l'instance unique de l'environnement.
     * Crée l'instance si elle n'existe pas (Lazy Initialization)
     * 
     * @return L'instance unique de l'environnement
     */
    public static Environnement getInstance() {
        if (instance == null) {
            instance = new Environnement();
        }
        return instance;
    }
    
    /**
     * Réinitialise l'environnement.
     * Utile pour les tests ou pour démarrer une nouvelle partie.
     */
    public static void reset() {
        instance = null;
    }

    /**
     * Initialise le monde de jeu avec la carte spécifiée.
     * Cette méthode charge la carte, crée le joueur et les entités.
     * 
     * Principe MVC:
     * - Le modèle charge les DONNÉES de la carte (grille, collisions)
     * - Le modèle crée les entités (joueur, NPCs, ennemis)
     * - Le modèle NE charge PAS les images (responsabilité de la Vue)
     * 
     * @param cheminCarte Chemin vers le fichier CSV de la carte
     * @throws IOException Si la carte ne peut pas être chargée
     */
    public void initialiserMonde(String cheminCarte) throws IOException {
        // 1. Chargement du terrain (données uniquement, pas d'images)
        System.out.println("Chargement de la carte: " + cheminCarte);
        int[][] grilleCarte = ChargeurCarte.chargerCarteDepuisCSV(cheminCarte);
        this.carte = new Carte(grilleCarte);
        notifierObservateurs(TypeChangement.CARTE_CHARGEE);
        
        // 2. Création du joueur via Factory Pattern
        double xJoueur = 320;
        int colonneJoueur = (int) (xJoueur / ConstantesJeu.TAILLE_TUILE);
        int ligneSolJoueur = carte.chercherLigneSol(colonneJoueur);
        double yJoueur = ligneSolJoueur != -1 
            ? ligneSolJoueur * ConstantesJeu.TAILLE_TUILE - ConstantesJeu.TAILLE_SPRITE 
            : 56;
        
        this.joueur = personnageFactory.createJoueur(xJoueur, yJoueur);
        System.out.println("Joueur créé à la position: (" + xJoueur + ", " + yJoueur + ")");
        notifierObservateurs(TypeChangement.JOUEUR_CREE);
        
        // 3. Création des NPCs via Factory Pattern
        creerNPCs();
        
        // 4. Création du loup (ennemi) via Factory Pattern
        creerLoup();
        
        // 5. Création des ressources (arbres, roches, etc.)
        creerRessources();
        
        System.out.println("Environnement initialisé avec succès.");
    }
    
    /**
     * Crée les NPCs (Guide, Forgeron) via le Factory Pattern
     */
    private void creerNPCs() {
        // Guide
        double xGuide = ConstantesJeu.POSITION_GUIDE_X;
        int colGuide = (int) (xGuide / ConstantesJeu.TAILLE_TUILE);
        int ligneSolGuide = carte.chercherLigneSol(colGuide);
        double yGuide = ligneSolGuide != -1
            ? ligneSolGuide * ConstantesJeu.TAILLE_TUILE - ConstantesJeu.HAUTEUR_GUIDE
            : 100;
        
        this.guide = personnageFactory.createGuide(xGuide, yGuide);
        notifierObservateurs(TypeChangement.NPC_CREE);
        
        // Forgeron
        double xForgeron = ConstantesJeu.POSITION_FORGERON_X;
        double yForgeron = 380;
        
        this.forgeron = personnageFactory.createForgeron(xForgeron, yForgeron);
        notifierObservateurs(TypeChangement.NPC_CREE);
        
        System.out.println("NPCs créés (Guide, Forgeron)");
    }
    
    /**
     * Crée le loup (ennemi) via le Factory Pattern
     */
    private void creerLoup() {
        double xLoup = 1500;
        int colLoup = (int) (xLoup / ConstantesJeu.TAILLE_TUILE);
        int ligneSolLoup = carte.chercherLigneSol(colLoup);
        double yLoup = ligneSolLoup != -1
            ? ligneSolLoup * ConstantesJeu.TAILLE_TUILE - 64
            : 56;
        
        // Create wolf with default dimensions (64x64)
        this.loup = personnageFactory.createLoup(xLoup, yLoup, 64, 64, 20);
        notifierObservateurs(TypeChangement.ENNEMI_CREE);
        
        System.out.println("Loup créé à la position: (" + xLoup + ", " + yLoup + ")");
    }
    
    /**
     * Crée les ressources du monde (arbres, roches, etc.)
     * Ces ressources sont des données de modèle, pas des éléments visuels
     */
    private void creerRessources() {
        ressources.clear();
        
        double baseX = loup.getX() - 100;
        
        // Arbres
        for (int i = 0; i < 2; i++) {
            double xR = baseX + i * 40;
            int col = (int) (xR / ConstantesJeu.TAILLE_TUILE);
            int ligne = carte.chercherLigneSol(col);
            double yR = ligne != -1 ? ligne * ConstantesJeu.TAILLE_TUILE - 64 : 56;
            ressources.add(new RessourceModele("Arbre", "Bois", xR, yR, 64, 64));
        }
        
        // Cannes
        for (int i = 0; i < 2; i++) {
            double xR = baseX + 80 + i * 40;
            int col = (int) (xR / ConstantesJeu.TAILLE_TUILE);
            int ligne = carte.chercherLigneSol(col);
            double yR = ligne != -1 ? ligne * ConstantesJeu.TAILLE_TUILE - 64 : 56;
            ressources.add(new RessourceModele("Canne", "Bois", xR, yR, 64, 64));
        }
        
        // Roches
        for (int i = 0; i < 2; i++) {
            double xR = baseX + 160 + i * 40;
            int col = (int) (xR / ConstantesJeu.TAILLE_TUILE);
            int ligne = carte.chercherLigneSol(col);
            double yR = ligne != -1 ? ligne * ConstantesJeu.TAILLE_TUILE - 64 : 56;
            ressources.add(new RessourceModele("Roche", "Pierre", xR, yR, 64, 64));
        }
        
        notifierObservateurs(TypeChangement.RESSOURCES_CHARGEES);
        System.out.println("Ressources créées: " + ressources.size() + " ressources");
    }
    
    /**
     * Exécute une action dans le monde de jeu.
     * Le contrôleur récupère l'action et la donne à l'environnement.
     * 
     * @param typeAction Type d'action à exécuter
     * @param parametres Paramètres de l'action
     */
    public void executerAction(String typeAction, Object... parametres) {
        switch (typeAction) {
            case "ATTAQUER" -> {
                // Le joueur attaque (sans paramètre car la logique est dans le modèle)
                joueur.attaquer();
                notifierObservateurs(TypeChangement.COMBAT);
            }
            case "RECOLTER_RESSOURCE" -> {
                if (parametres.length >= 1 && parametres[0] instanceof RessourceModele) {
                    RessourceModele ressource = (RessourceModele) parametres[0];
                    if (ressource.interagir(joueur)) {
                        ressources.remove(ressource);
                        notifierObservateurs(TypeChangement.RESSOURCE_RECOLTEE);
                    }
                }
            }
            case "INTERAGIR_NPC" -> {
                // Logique d'interaction avec NPC
                notifierObservateurs(TypeChangement.DIALOGUE);
            }
            default -> System.err.println("Action inconnue: " + typeAction);
        }
    }
    
    /**
     * Met à jour l'environnement (appelé par le contrôleur à chaque frame)
     */
    public void mettreAJour() {
        // Mise à jour du joueur
        if (joueur != null) {
            joueur.mettreAJour();
        }
        
        // Mise à jour du loup (IA)
        if (loup != null && loup.getPointsDeVie() > 0) {
            loup.mettreAJourIA(joueur, carte);
        }
        
        // Autres mises à jour...
    }

    // ========== Observer Pattern ==========
    
    @Override
    public void ajouterObservateur(Observateur observateur) {
        sujetObservable.ajouterObservateur(observateur);
    }

    @Override
    public void retirerObservateur(Observateur observateur) {
        sujetObservable.retirerObservateur(observateur);
    }

    @Override
    public void notifierObservateurs(TypeChangement type) {
        sujetObservable.notifierObservateurs(type);
    }

    // ========== Getters ==========
    
    public Carte getCarte() { return carte; }
    public Joueur getJoueur() { return joueur; }
    public Loup getLoup() { return loup; }
    public Guide getGuide() { return guide; }
    public Forgeron getForgeron() { return forgeron; }
    public List<RessourceModele> getRessources() { return Collections.unmodifiableList(ressources); }
}
