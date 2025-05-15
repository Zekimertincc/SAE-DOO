package fr.iut.groupe.terraria.demo.modele.personnage;

import java.util.ArrayList;
import java.util.List;

public class Quetes {
    private List<Quete> quetes;

    public Quetes() {
        quetes = new ArrayList<>();
    }

    public void ajouterQuete(Quete quete) {
        quetes.add(quete);
    }

    public List<Quete> getQuetes() {
        return quetes;
    }

    public List<Quete> getQuetesTerminees() {
        List<Quete> terminees = new ArrayList<>();
        for (Quete q : quetes) {
            if (q.estTerminee()) {
                terminees.add(q);
            }
        }
        return terminees;
    }

    public List<Quete> getQuetesEnCours() {
        List<Quete> enCours = new ArrayList<>();
        for (Quete q : quetes) {
            if (!q.estTerminee()) {
                enCours.add(q);
            }
        }
        return enCours;
    }
}
