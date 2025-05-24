package fr.iut.groupe.terraria.demo.modele.item.nourriture;

import fr.iut.groupe.terraria.demo.modele.personnage.EtatTemporaire;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;

public class Poulet extends Nourriture {
    public Poulet() {
        super("Poulet", 2);
    }

    @Override
    public void appliquerEffetSecondaire(Joueur joueur){
        EtatTemporaire etat = joueur.getEtatTemporaire();
        etat.appliquerEffet(1.5, true, false); // invincible
        etat.setEffetFin(30000);
    }
}

