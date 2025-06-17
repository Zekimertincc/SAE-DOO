package universite_paris8.iut.dagnetti.junglequest.modele.personnage;

import universite_paris8.iut.dagnetti.junglequest.modele.Inventaire;
import universite_paris8.iut.dagnetti.junglequest.modele.item.Coffre;
import universite_paris8.iut.dagnetti.junglequest.modele.item.Item;
import universite_paris8.iut.dagnetti.junglequest.modele.item.Recompense;
import universite_paris8.iut.dagnetti.junglequest.modele.item.equipement.arme.Arc;

public class Quete extends Recompense {
    private String description;
    private String cible;        // "Loup", "Pomme", etc.
    private int objectif;
    private int progression;
    private boolean terminee;
    private boolean recompenseDonnee;

    public Quete(Inventaire inventaire, String description, String cible, int objectif) {
        super(inventaire);
        this.description = description;
        this.cible = cible;
        this.objectif = objectif;
        this.progression = 0;
        this.terminee = false;
        this.recompenseDonnee = false;
    }

    public void progresser() {
        if (!terminee) {
            progression++;
            if (progression >= objectif) {
                terminee = true;
            }
        }
    }

    public void donnerRecompense() {
        Item randomItem = randomItem();
        if (terminee && !recompenseDonnee && randomItem != null) {
            inventaire.ajouterItem(randomItem);
            recompenseDonnee = true;
        }
    }
    
// ------------------------------------------------------------------------------------------------------------------------------------------------
    public boolean estTerminee() {
        return terminee;
    }

    public String getCible() {
        return cible;
    }

    public String getDescription() {
        return description;
    }

    public int getProgression() {
        return progression;
    }

    public int getObjectif() {
        return objectif;
    }
}


