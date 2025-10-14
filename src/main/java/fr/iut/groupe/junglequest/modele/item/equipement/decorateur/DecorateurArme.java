package fr.iut.groupe.junglequest.modele.item.equipement.decorateur;

import fr.iut.groupe.junglequest.modele.item.Inventaire;
import fr.iut.groupe.junglequest.modele.item.equipement.Equipement;
import fr.iut.groupe.junglequest.modele.item.equipement.arme.Arme;

public abstract class DecorateurArme extends Arme {

    protected Arme arme;

    public DecorateurArme(String nom, int degats, int portee, Arme arme) {
        super(nom, degats, portee);
        this.arme = arme;
    }

    public int porteeTotal() {
        return arme.getPortee() + this.portee;
    }

    public int degatsTotal() {
        return arme.getDegats()+ this.getDegats();
    }

    @Override
    public boolean seConstruit(Equipement e) {
        return false;
    }

}
