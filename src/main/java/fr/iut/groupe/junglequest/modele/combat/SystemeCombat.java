package fr.iut.groupe.junglequest.modele.combat;

import fr.iut.groupe.junglequest.modele.Ciblable;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;

/**
 * Gestion des attaques et des dégâts dans le jeu
 */
public class SystemeCombat {
    /**
     * Gère une attaque du joueur vers un adversaire
     * @param attaquant L'entité qui attaque
     * @param cible La cible de l'attaque
     * @param distance La distance entre l'attaquant et la cible
     * @return true si l'attaque a touché
     */
    public static boolean gererAttaque(Ciblable attaquant, Ciblable cible, double distance) {
        if (distance <= attaquant.getPorteeAttaque()) {
            int degats = attaquant.calculerDegats(cible);
            cible.subirDegats(degats);
            return true;
        }
        return false;
    }

    /**
     * Calcule les dégâts en fonction de l'équipement et des bonus
     * @param base Les dégâts de base
     * @param attaquant L'entité qui attaque
     * @param cible La cible de l'attaque
     * @return Les dégâts finaux à infliger
     */
    public static int calculerDegats(int base, Ciblable attaquant, Ciblable cible) {
        double multiplicateur = 1.0;
        
        // Bonus d'équipement
        if (attaquant instanceof Joueur) {
            Joueur j = (Joueur) attaquant;
            multiplicateur *= j.getBonusEquipement();
        }
        
        // Réduction de dégâts du bouclier
        if (cible instanceof Joueur) {
            Joueur j = (Joueur) cible;
            if (j.isBouclierActif()) {
                multiplicateur *= 0.5; // 50% de réduction avec le bouclier
            }
        }
        
        return (int) (base * multiplicateur);
    }
}