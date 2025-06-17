package fr.iut.groupe.terraria.demo.modele.donnees;

public final class ConstantesJeu {

    // ➤ Gerçek tileset içindeki kare boyutu
    public static final int TAILLE_TUILE_ORIG = 32;

    // ➤ Oyuna gösterilen boyut (ekran için küçültülmüş görünüm)
    public static final int TAILLE_TUILE = 16;

    public static final int TAILLE_SPRITE = 24;     // Karakter sprite'ının gösterilen boyutu
    public static final int LARGEUR_JOUEUR = 24;

    public static final int VIE_MAX_JOUEUR = 100;
    public static int VITESSE_JOUEUR = 1;
    public static final double GRAVITE = 0.5;
    public static final double VITESSE_CHUTE_MAX = 2;
    public static double IMPULSION_SAUT = 15;

    public static final int DELAI_FRAME = 8;
    public static final int DUREE_ATTAQUE = 8 * DELAI_FRAME;

    private ConstantesJeu() {}
}
