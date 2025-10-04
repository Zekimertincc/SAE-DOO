package fr.iut.groupe.junglequest.modele.item.nourriture;

import fr.iut.groupe.junglequest.modele.personnages.EtatTemporaire;
import fr.iut.groupe.junglequest.modele.personnages.Joueur;

public class Pomme extends NourritureB {
    public Pomme() {
        super("Pomme", 1);
    }

    @Override
    public void appliquerEffetSecondaire(Joueur joueur){
        EtatTemporaire etat = joueur.getEtatTemporaire();
        etat.appliquerEffet(3.0, false, false); // vitesse
        etat.setEffetFin(30000);
    }
}

