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

    /** Dimensions des sprites du guide (5 frames de 32x48) */
    public static final int LARGEUR_GUIDE = 32;
    public static final int HAUTEUR_GUIDE = 48;
    public static final int NB_FRAMES_GUIDE = 5;

    /** Dimensions des sprites du forgeron (40 frames de 96x64) */
    public static final int LARGEUR_FORGERON = 96;
    public static final int HAUTEUR_FORGERON = 64;
    public static final int NB_FRAMES_FORGERON = 40;

    /** Position horizontale du guide sur la carte */
    public static final double POSITION_GUIDE_X = 260;

    /** Position horizontale du forgeron sur la carte */
    public static final double POSITION_FORGERON_X = 520;

    /**
     * Identifiants des tuiles considérées comme non solides (pas de collision).
     * Ces valeurs correspondent à l'id dans le fichier CSV de la carte.
     */
    public static final java.util.Set<Integer> TUILES_NON_SOLIDES = java.util.Set.of(
            0, 1, 2,
            56, 57, 58,
            112, 113, 114,
            168, 169, 170,
            280, 281, 282
    );

    /** Quantité maximale de points de vie du joueur */
    public static final int VIE_MAX_JOUEUR = 100;

    /** Quantité maximale de points de vie du loup */
    public static final int VIE_MAX_LOUP = 150;

    /** Dégâts infligés au loup par une attaque du joueur */
    public static final int DEGATS_JOUEUR_LOUP = 10;

    /** Dégâts supplémentaires appliqués lors d'un combo */
    public static final int DEGATS_COMBO_SUPPLEMENTAIRES = 5;

    /** Portée maximale d'une attaque du joueur (en pixels) */
    public static final double PORTEE_ATTAQUE_JOUEUR = 80;


    /**
     * Distance à laquelle le loup cesse d'avancer avant une attaque (en pixels).
     * Laisser un peu plus d'espace permet de fluidifier l'animation lorsque le
     * joueur s'éloigne brusquement.
     */
    public static final double DISTANCE_ARRET_LOUP = 50;

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

    /**
     * Durée pendant laquelle l'animation de dégâts du joueur reste affichée
     * après avoir été touché.
     * Une valeur plus faible rend la réaction plus dynamique.
     */
    public static final int DUREE_DEGATS_JOUEUR = DELAI_FRAME;

    /**
     * Temps d'attente du loup lorsqu'il est à portée du joueur avant de lancer
     * une nouvelle attaque (en frames). L'attente est d'environ deux secondes
     * à 60 FPS.
     */
    public static final int DELAI_AVANT_ATTAQUE_LOUP = DELAI_FRAME;

    // Constructeur privé pour empêcher l’instanciation
    private ConstantesJeu() {}
}
