package universite_paris8.iut.dagnetti.junglequest.modele.item.nourriture;

import universite_paris8.iut.dagnetti.junglequest.modele.personnages.EtatTemporaire;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Joueur;

public class Champignon extends Nourriture {
    public Champignon() {
        super("Champignon", -1);
    }

    @Override
    public void appliquerEffetSecondaire(Joueur joueur){
        EtatTemporaire etat = joueur.getEtatTemporaire();
        etat.appliquerEffet(1.5, false, true); // vulnérable
        etat.setEffetFin(30000);
    }

}
