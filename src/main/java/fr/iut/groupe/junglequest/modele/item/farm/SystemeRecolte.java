package fr.iut.groupe.junglequest.modele.item.farm;

import fr.iut.groupe.junglequest.modele.personnages.Joueur;
import fr.iut.groupe.junglequest.modele.item.Inventaire;

/**
 * Gestion du système de récolte des ressources.
 * 
 * Architecture MVC:
 * - Cette classe est dans le Model (logique métier)
 * - Utilise RessourceModele (pas d'éléments visuels)
 * - Gère la logique de récolte des ressources
 */
public class SystemeRecolte {
    /**
     * Tente de récolter une ressource
     * @param ressource La ressource à récolter
     * @param joueur Le joueur qui récolte
     * @param x Position X du clic
     * @param y Position Y du clic
     * @return true si la récolte a réussi
     */
    public static boolean tenterRecolte(RessourceModele ressource, Joueur joueur, double x, double y) {
        // Vérification de la position
        if (!estAPortee(ressource, x, y)) {
            return false;
        }

        // Vérification de l'équipement requis
        if (!ressource.peutEtreRecolte(joueur.getEquipementActuel())) {
            return false;
        }

        // Tentative d'ajout à l'inventaire
        if (joueur.getInventaire().peutAjouter(ressource)) {
            joueur.getInventaire().ajouter(ressource);
            return true;
        }

        return false;
    }

    /**
     * Vérifie si une position est à portée de récolte d'une ressource
     */
    private static boolean estAPortee(RessourceModele ressource, double x, double y) {
        double distance = Math.sqrt(
            Math.pow(x - ressource.getX(), 2) + 
            Math.pow(y - ressource.getY(), 2)
        );
        return distance <= ressource.getPorteeRecolte();
    }
}