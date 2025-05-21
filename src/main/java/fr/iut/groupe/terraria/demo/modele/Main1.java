package fr.iut.groupe.terraria.demo.modele;

import fr.iut.groupe.terraria.demo.modele.item.equipement.arme.Arc;
import fr.iut.groupe.terraria.demo.modele.item.equipement.outil.Hache;
import fr.iut.groupe.terraria.demo.modele.personnage.Joueur;
import fr.iut.groupe.terraria.demo.modele.ressource.Arbre;

public class Main1 {
    public static void main (String[] args){
        Joueur joueur = new Joueur(1, 1, 5);

        Hache hache = new Hache();
        Arc arc = new Arc();
        joueur.setEquipementActuel(hache);

        // 20hp
        Arbre arbre = new Arbre(2, 2);
        joueur.utiliserEquipementSur(arbre);
        // 14hp
        System.out.println(arbre.getVie());

        joueur.setEquipementActuel(arc);
        joueur.utiliserEquipementSur(arbre);
        //4hp
        System.out.println(arbre.getVie());


        Arbre arbre2 = new Arbre(100, 100);
        joueur.utiliserEquipementSur(arbre2);
        System.out.println(arbre2.getVie());

    }
}
