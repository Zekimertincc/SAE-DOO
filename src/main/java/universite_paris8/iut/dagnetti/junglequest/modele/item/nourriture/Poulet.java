package universite_paris8.iut.dagnetti.junglequest.modele.item.nourriture;

import universite_paris8.iut.dagnetti.junglequest.modele.personnages.EtatTemporaire;
import universite_paris8.iut.dagnetti.junglequest.modele.personnages.Joueur;

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

