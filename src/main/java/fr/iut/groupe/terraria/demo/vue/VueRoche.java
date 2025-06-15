package fr.iut.groupe.terraria.demo.vue;

import fr.iut.groupe.terraria.demo.modele.ressource.Roche;


public class VueRoche extends VueRessource<Roche> {

    public VueRoche(Roche roche) {
        super(roche, "/fr/iut/groupe/terraria/demo/roche.png", 64, 64);
    }
}
