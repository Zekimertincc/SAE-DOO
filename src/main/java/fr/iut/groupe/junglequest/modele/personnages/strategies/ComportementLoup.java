package fr.iut.groupe.junglequest.modele.personnages.strategies;

import fr.iut.groupe.junglequest.modele.carte.Carte;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;
import fr.iut.groupe.junglequest.modele.personnages.Loup;

/**
 * Interface définissant une stratégie de comportement pour les personnages non-joueurs.
 * Suit le pattern Strategy pour permettre différents comportements interchangeables.
 */
public interface ComportementLoup {
    /**
     * Met à jour le comportement du loup.
     * @param loup Le loup à contrôler
     * @param joueur Le joueur ciblé
     * @param carte La carte du jeu pour la navigation
     */
    void mettreAJour(Loup loup, Joueur joueur, Carte carte);

    /**
     * Retourne le nom de la stratégie pour le debugging et l'interface utilisateur.
     * @return Le nom descriptif de la stratégie
     */
    String getNom();

    /**
     * Indique si la stratégie est agressive envers le joueur.
     * @return true si la stratégie est agressive, false sinon
     */
    boolean estAgressif();
}