package fr.iut.groupe.junglequest.modele.observateurs;

/**
 * Interface pour les observateurs du pattern Observer.
 * Permet aux vues de réagir aux changements du modèle.
 */
public interface Observateur {
    /**
     * Appelé quand un changement survient dans le modèle observé.
     * @param sujet Le sujet qui a changé
     * @param type Le type de changement survenu
     */
    void mettreAJour(SujetObserve sujet, TypeChangement type);
}
