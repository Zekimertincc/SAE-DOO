package fr.iut.groupe.junglequest.modele.item.farm;

import fr.iut.groupe.junglequest.modele.personnages.Joueur;
import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;

/**
 * Modèle d'une ressource dans le jeu (Model - MVC)
 * 
 * Responsabilités:
 * - Gestion des données de la ressource (position, type, récompense)
 * - Logique métier (peut-on récolter? avec quel outil?)
 * - NE contient PAS d'éléments visuels (pas de ImageView)
 * 
 * Architecture MVC:
 * - Le Model NE connaît PAS la Vue
 * - Les éléments visuels sont gérés par VueRessource
 * - La communication avec la vue se fait via Observer Pattern
 * 
 * Une ressource représente un élément récoltable sur la carte:
 * - Arbre (donne du Bois)
 * - Roche (donne de la Pierre)
 * - Canne (donne du Bois)
 * - etc.
 */
public class RessourceModele {
    private final String nom;              // ex: "Arbre", "Canne", "Roche"
    private final String itemRecompense;   // ex: "Bois", "Pierre"
    private final double x;
    private final double y;
    private final double largeur;
    private final double hauteur;
    private final int porteeRecolte;       // Distance max pour récolter
    private boolean recoltee;              // État de la ressource
    
    /**
     * Constructeur complet
     */
    public RessourceModele(String nom, String itemRecompense, double x, double y, double largeur, double hauteur) {
        this.nom = nom;
        this.itemRecompense = itemRecompense;
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.porteeRecolte = 50;
        this.recoltee = false;
    }
    
    /**
     * Constructeur simplifié avec taille par défaut
     */
    public RessourceModele(String nom, String itemRecompense, double x, double y) {
        this(nom, itemRecompense, x, y, 64, 64);
    }
    
    /**
     * Vérifie si la ressource peut être récoltée avec l'équipement donné.
     * 
     * Logique métier:
     * - Arbre → Hache
     * - Roche → Pioche
     * - Canne → Aucun outil nécessaire
     * 
     * @param equipement L'équipement du joueur
     * @return true si la ressource peut être récoltée, false sinon
     */
    public boolean peutEtreRecolte(Equipement equipement) {
        return switch (nom.toLowerCase()) {
            case "arbre" -> equipement != null && equipement.getNom().equalsIgnoreCase("hache");
            case "roche" -> equipement != null && equipement.getNom().equalsIgnoreCase("pioche");
            case "canne" -> true; // Pas besoin d'outil
            default -> false;
        };
    }
    
    /**
     * Vérifie si le joueur est à portée pour récolter cette ressource.
     * 
     * @param xJoueur Position X du joueur
     * @param yJoueur Position Y du joueur
     * @return true si le joueur est à portée, false sinon
     */
    public boolean estAPortee(double xJoueur, double yJoueur) {
        double distance = Math.sqrt(Math.pow(x - xJoueur, 2) + Math.pow(y - yJoueur, 2));
        return distance <= porteeRecolte;
    }
    
    /**
     * Interaction avec la ressource.
     * Tente de récolter la ressource si le joueur a le bon équipement.
     * 
     * @param joueur Le joueur qui interagit
     * @return true si la ressource a été récoltée, false sinon
     */
    public boolean interagir(Joueur joueur) {
        if (recoltee) {
            return false; // Déjà récoltée
        }
        
        // Cas spécial: Arbre peut être cassé sans outil ou avec hache
        boolean peutCasser = nom.equalsIgnoreCase("Arbre") 
            || nom.equalsIgnoreCase("Canne")
            || joueur.getInventaire().contient("Hache", 1)
            || joueur.getInventaire().contient("Pioche", 1);
        
        if (peutCasser) {
            recoltee = true;
            joueur.getInventaire().ajouterItem(itemRecompense, 1);
            System.out.println("Ressource récoltée: " + nom + " → " + itemRecompense);
            return true;
        }
        
        System.out.println("Impossible de récolter " + nom + " sans le bon outil.");
        return false;
    }
    
    // ========== Getters ==========
    
    public String getNom() {
        return nom;
    }
    
    public String getItemRecompense() {
        return itemRecompense;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public double getLargeur() {
        return largeur;
    }
    
    public double getHauteur() {
        return hauteur;
    }
    
    public int getPorteeRecolte() {
        return porteeRecolte;
    }
    
    public boolean estRecoltee() {
        return recoltee;
    }
}

