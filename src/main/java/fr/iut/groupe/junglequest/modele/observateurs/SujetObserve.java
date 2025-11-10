package fr.iut.groupe.junglequest.modele.observateurs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Classe abstraite représentant un sujet observé dans le pattern Observer.
 * Utilise CopyOnWriteArrayList pour une thread-safety lors des notifications.
 */
public interface SujetObserve {
    /**
     * Ajoute un observateur à la liste.
     * @param obs L'observateur à ajouter
     */
    void ajouterObservateur(Observateur obs);

    /**
     * Retire un observateur de la liste.
     * @param obs L'observateur à retirer
     */
    void retirerObservateur(Observateur obs);

    /**
     * Notifie tous les observateurs d'un changement spécifique.
     * @param type Le type de changement survenu
     */
    void notifierObservateurs(TypeChangement type);
}
