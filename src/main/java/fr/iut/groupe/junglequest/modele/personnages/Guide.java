package fr.iut.groupe.junglequest.modele.personnages;

import fr.iut.groupe.junglequest.modele.donnees.ConstantesJeu;

/**
 * Guide NPC - Personnage non joueur qui fournit des informations au joueur
 * 
 * Architecture MVC:
 * - Cette classe est dans le Model, elle ne contient PAS d'éléments visuels
 * - Les éléments visuels (sprites, animations) sont gérés par la Vue
 * - La communication avec la Vue se fait via la position et les propriétés
 */
public class Guide extends Personnage {
    
    /**
     * Constructeur du Guide
     * 
     * @param x Position X du guide
     * @param y Position Y du guide
     */
    public Guide(double x, double y) {
        super(x, y, ConstantesJeu.LARGEUR_GUIDE, ConstantesJeu.HAUTEUR_GUIDE);
    }
    
    /**
     * Message de dialogue du guide
     * 
     * @return Le message à afficher
     */
    public String getMessageDialogue() {
        return "Bonjour voyageur ! Bienvenue dans la jungle.";
    }
}