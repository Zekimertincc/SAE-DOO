package fr.iut.groupe.junglequest.modele.personnages.etats;

import fr.iut.groupe.junglequest.modele.personnages.Joueur;

/**
 * Interface de base pour le pattern State gérant les états du joueur
 */
public interface EtatJoueur extends EtatPersonnage {
    /**
     * Met à jour l'état du joueur
     * @param joueur Le joueur à mettre à jour
     */
    void mettreAJour(Joueur joueur);
    
    /**
     * Obtient l'identifiant d'animation correspondant à cet état
     * @return L'identifiant de l'animation
     */
    String getAnimationId();
    
    /**
     * Indique si le joueur peut attaquer dans cet état
     * @return true si le joueur peut attaquer
     */
    boolean peutAttaquer();
    
    /**
     * Indique si le joueur peut se déplacer dans cet état
     * @return true si le joueur peut se déplacer
     */
    boolean peutDeplacer();
}