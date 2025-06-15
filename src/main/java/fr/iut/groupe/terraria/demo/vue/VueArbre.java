package fr.iut.groupe.terraria.demo.vue;

import fr.iut.groupe.terraria.demo.modele.ressource.Arbre;


public class VueArbre extends VueRessource<Arbre> {

    public VueArbre(Arbre arbre) {
        super(arbre, "/fr/iut/groupe/terraria/demo/tree.png", 96, 64);
    }
}
