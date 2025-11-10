package fr.iut.groupe.junglequest.modele.observateurs;

/**
 * Wrapper pour l'ancien SujetObserve pour assurer la compatibilité avec le nouveau système
 */
public class SujetObserveCompat implements SujetObserve {
    private final SujetObserveImpl delegate = new SujetObserveImpl();
    
    @Override
    public void ajouterObservateur(Observateur obs) {
        delegate.ajouterObservateur(obs);
    }

    @Override
    public void retirerObservateur(Observateur obs) {
        delegate.retirerObservateur(obs);
    }

    @Override
    public void notifierObservateurs(TypeChangement type) {
        delegate.notifierObservateurs(type);
    }
    
    public void notifierObservateurs() {
        notifierObservateurs(TypeChangement.POINTS_DE_VIE);
    }
}