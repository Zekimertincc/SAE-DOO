package fr.iut.groupe.junglequest.modele.item.equipement.decorateur;

import fr.iut.groupe.junglequest.modele.item.equipement.arme.Arme;

public class Viseur extends DecorateurArme{
    public Viseur(Arme arme ) {
        super("Viseur", 0, 50, arme);
    }
}
