package fr.iut.groupe.terraria.demo.modele.item.nourriture;

import fr.iut.groupe.terraria.demo.modele.personnage.EtatTemporaire;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;

public class Pomme extends Nourriture {
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

