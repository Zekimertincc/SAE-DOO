package fr.iut.groupe.junglequest.modele.item.equipement.decorateur;

import fr.iut.groupe.junglequest.modele.item.equipement.arme.Arme;

public class Chargeur extends DecorateurArme {

    public Chargeur(Arme arme) {
        super("Chargeur", 5, 0, arme);
    }
}
