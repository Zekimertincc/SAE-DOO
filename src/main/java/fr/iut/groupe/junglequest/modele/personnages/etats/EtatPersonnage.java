package fr.iut.groupe.junglequest.modele.personnages.etats;

import fr.iut.groupe.junglequest.modele.personnages.Joueur;

/**
 * Base interface for character states.
 * Following the State pattern, each state encapsulates its own behavior.
 * 
 * Architecture MVC:
 * - NO ImageView or UI elements (pure Model)
 * - States control behavior/logic only
 * - Animation is handled by the Controller/View
 */
public interface EtatPersonnage {

    /**
     * Called when entering this state
     * @param joueur The player character
     */
    void entrer(Joueur joueur);

    /**
     * Called when exiting this state
     * @param joueur The player character
     */
    void sortir(Joueur joueur);

    /**
     * Handles movement in this state
     * @param joueur The player character
     * @param gauche Whether moving left
     * @param droite Whether moving right
     */
    void gererMouvement(Joueur joueur, boolean gauche, boolean droite);

    /**
     * Handles jumping in this state
     * @param joueur The player character
     */
    void gererSaut(Joueur joueur);

    /**
     * Gets the name of this state
     * @return State name
     */
    String getNom();
}