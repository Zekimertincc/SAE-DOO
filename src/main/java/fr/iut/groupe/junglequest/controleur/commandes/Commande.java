package fr.iut.groupe.junglequest.controleur.commandes;

/**
 * Interface Command pour le pattern Command.
 * Représente une action qui peut être exécutée dans le jeu.
 */
public interface Commande {
    /**
     * Exécute la commande.
     */
    void executer();
}