package fr.iut.groupe.junglequest.modele.observateurs;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

public class SujetObserveImpl implements SujetObserve {
    private final List<Observateur> observateurs = new CopyOnWriteArrayList<>();

    @Override
    public void ajouterObservateur(Observateur obs) {
        if (obs != null && !observateurs.contains(obs)) {
            observateurs.add(obs);
        }
    }

    @Override
    public void retirerObservateur(Observateur obs) {
        observateurs.remove(obs);
    }

    @Override
    public void notifierObservateurs(TypeChangement type) {
        for (Observateur obs : observateurs) {
            obs.mettreAJour(this, type);
        }
    }
}