package universite_paris8.iut.dagnetti.junglequest.modele.donnees;

/**
 * Cette classe contient toutes les constantes générales du jeu :
 * physiques, visuelles ou temporelles.
 */
public final class ConstantesJeu {

    // --- Dimensions ---
    /** Taille (en pixels) d’une tuile de la carte */
    public static final int TAILLE_TUILE = 32;

    /** Taille d’une frame dans les spritesheets (associée aux animations du joueur) */
    public static final int TAILLE_SPRITE = 56;

    /** Largeur du joueur (en pixels, utilisée pour le centrage) */
    public static final int LARGEUR_JOUEUR = 56;

    /** Quantité maximale de points de vie du joueur */
    public static final int VIE_MAX_JOUEUR = 100;

    /** Quantité maximale de points de vie du loup */
    public static final int VIE_MAX_LOUP = 150;

    /** Dégâts infligés au loup par une attaque du joueur */
    public static final int DEGATS_JOUEUR_LOUP = 10;

    /** Distance à laquelle le loup cesse d'avancer avant une attaque (en pixels) */
    public static final double DISTANCE_ARRET_LOUP = 30;

    // --- Mouvements et physique ---
    /** Vitesse horizontale du joueur (modifiable via le menu de paramètres) */
    public static int VITESSE_JOUEUR = 1;

    /** Accélération gravitationnelle appliquée chaque frame */
    public static final double GRAVITE = 0.5;

    /** Vitesse verticale maximale en chute libre */
    public static final double VITESSE_CHUTE_MAX = 2;

    /** Force de l’impulsion de saut du joueur (modifiable) */
    public static double IMPULSION_SAUT = 10;

    // --- Animation ---
    /** Délai entre deux frames d’animation (en nombre de frames) */
    public static final int DELAI_FRAME = 8;

    /**
     * Durée minimale entre deux attaques successives du loup (en frames).
     * Augmenter cette valeur permet de laisser davantage de temps avant
     * qu'un nouvel assaut puisse être déclenché.
     */
    public static final int DUREE_ATTAQUE = 16 * DELAI_FRAME;

    // Constructeur privé pour empêcher l’instanciation
    private ConstantesJeu() {}
}
