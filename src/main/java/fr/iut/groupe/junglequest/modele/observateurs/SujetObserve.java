package fr.iut.groupe.junglequest.modele.observateurs;

import java.util.ArrayList;
import java.util.List;

public abstract class SujetObserve {
    private final List<Observateur> observateurs = new ArrayList<>();

    public void ajouterObservateur(Observateur obs) {
        observateurs.add(obs);
    }

    public void retirerObservateur(Observateur obs) {
        observateurs.remove(obs);
    }

    public void notifierObservateurs() {
        for (Observateur obs : observateurs) {
            obs.mettreAJour();
        }
    }
}
