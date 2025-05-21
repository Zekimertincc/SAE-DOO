package fr.iut.groupe.terraria.demo.modele.personnage;

import fr.iut.groupe.terraria.demo.modele.Inventaire;
import fr.iut.groupe.terraria.demo.modele.item.Coffre;
import fr.iut.groupe.terraria.demo.modele.item.Item;
import fr.iut.groupe.terraria.demo.modele.item.Recompense;
import fr.iut.groupe.terraria.demo.modele.item.equipement.arme.Arc;

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


