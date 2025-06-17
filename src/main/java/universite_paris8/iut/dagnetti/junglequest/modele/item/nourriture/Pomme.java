package universite_paris8.iut.dagnetti.junglequest.modele.item.nourriture;

import universite_paris8.iut.dagnetti.junglequest.modele.personnage.EtatTemporaire;
import universite_paris8.iut.dagnetti.junglequest.modele.personnage.Joueur;

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

